package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthRole;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.model.AuthUserPosition;
import co.perpixel.security.repository.AuthRoleRepository;
import co.perpixel.security.repository.AuthUnitRepository;
import co.perpixel.security.repository.AuthUserPositionRepository;
import co.perpixel.security.repository.AuthUserRepository;

import com.fasterxml.jackson.databind.JsonNode;
import eu.driver.model.core.RequestChangeOfTrialStage;
import org.flywaydb.core.internal.util.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.form.NewSessionForm;
import pl.com.itti.app.driver.form.UserForm;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.Languages;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.model.enums.AuthRoleType;
import pl.com.itti.app.driver.model.enums.ManagementRoleType;
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.repository.specification.TrialSessionSpecification;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import javax.persistence.EnumType;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static pl.com.itti.app.driver.util.SendToTestBed.sendToTestBed;
import static pl.com.itti.app.driver.util.TrialStage.*;

@Service
@Transactional
public class TrialSessionService {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Autowired
    private ObservationTypeService observationTypeService;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthUserPositionRepository authUserPositionRepository;

    @Autowired
    private AuthUnitRepository authUnitRepository;

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private TrialSessionManagerRepository trialSessionManagerRepository;

    @Transactional(readOnly = true)
    public TrialSession findOneByManager(long trialSessionId) {

        return trialSessionRepository.findById(trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));
    }

    @Transactional(readOnly = true)
    public Page<TrialSession> findAllByManager(Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();

        return trialSessionRepository.findAll(
                getTrialSessionManagerSpecifications(authUser),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public PageDTO<TrialSessionDTO.ActiveListItem> findByStatus(SessionStatus sessionStatus, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();

        Page<TrialSession> trialSessions = trialSessionRepository.findAll(
                getTrialSessionStatusSpecifications(authUser, sessionStatus),
                pageable);

        PageDTO<TrialSessionDTO.ActiveListItem> pageDTO = DTO.from(trialSessions, TrialSessionDTO.ActiveListItem.class);
        pageDTO.getData().forEach(d -> d.initHasAnswer = setInitAnswer(d, authUser));
        return pageDTO;
    }

    public TrialSession updateLastTrialStage(long trialSessionId, long lastTrialStageId) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);

        TrialSession trialSession = trialSessionRepository.findOne(trialSessionId);
        TrialStage trialStage = trialStageRepository.findById(lastTrialStageId)
                .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, lastTrialStageId));

        trialSession.setLastTrialStage(trialStage);
        return trialSessionRepository.save(trialSession);
    }

    public void changeStatus(long trialSessionId, String status) {
        Optional<TrialSession> trialSession = trialSessionRepository.findById(trialSessionId);

        trialSession.ifPresent(session -> {
            SessionStatus sessionStatus = SessionStatus.valueOf(status);
            session.setStatus(sessionStatus);
            trialSessionRepository.save(session);
        });
    }

    public List<String> createNewSession(NewSessionForm newSessionForm, boolean isEmail) {
        Map<String, TrialUser> users = new HashMap<>();
        Map<String, List<String>> emails = new HashMap<>();
        StringBuilder longestEmail = new StringBuilder();
        StringBuilder longestLogin = new StringBuilder();

        newSessionForm.getUsers().forEach(user -> {
            try {
                String password = StringUtils.left(UUID.randomUUID().toString(), 8);
                AuthUser authUser = createUser(user, password, newSessionForm.prefix);

                users.put(user.getEmail(), trialUserRepository.save(getTrialUser(authUser)));
                AuthRole authRole = StreamSupport.stream(authRoleRepository.findAll().spliterator(), false)
                        .filter(role -> role.getShortName().contains("ROLE_USER"))
                        .findFirst()
                        .orElse(null);

                authUser.setRoles(Stream.of(authRole).collect(Collectors.toSet()));
                if (isEmail) {
                    String trialName = trialRepository.findById(newSessionForm.getTrialId()).get().getName();
                    EmailService.send(authUser, password, trialName, user);
                } else {
                    emails.put(authUser.getEmail(), Arrays.asList(authUser.getLogin(), password));
                    longestEmail.replace(0, longestEmail.length(), authUser.getEmail().length() > longestEmail.length() ?
                            authUser.getEmail() : longestEmail.toString());
                    longestLogin.replace(0, longestLogin.length(), authUser.getLogin().length() > longestLogin.length() ?
                            authUser.getLogin() : longestLogin.toString());
                }
            } catch (Exception e) {
                throw new InternalServerException("Cannot crate new user: " + e.getMessage());
            }
        });

        TrialSession trialSession = TrialSession.builder().trial(trialRepository.findById(newSessionForm.getTrialId())
                .orElseThrow(() -> new EntityNotFoundException(Trial.class, newSessionForm.getTrialId())))
                .startTime(LocalDateTime.now())
                .status(SessionStatus.valueOf(newSessionForm.getStatus()))
                .pausedTime(LocalDateTime.now())
                .lastTrialStage(trialStageRepository.findByTrialIdAndName(newSessionForm.getTrialId(), newSessionForm.getInitialStage()).get())
                .build();

        trialSessionRepository.save(trialSession);
        TrialSessionManager trialSessionManager = TrialSessionManager.builder().trialSession(trialSession)
                .trialUser(trialUserRepository.findByAuthUser(trialUserService.getCurrentUser()))
                .build();

        trialSessionManagerRepository.save(trialSessionManager);

        for (UserForm userForm : newSessionForm.getUsers()) {
            for(String role : userForm.getRole()) {
                UserRoleSession userRoleSession = UserRoleSession.builder().trialUser(users.get(userForm.getEmail()))
                        .trialRole(trialRoleRepository.findFirstByTrialIdAndName(newSessionForm.getTrialId(), role).get())
                        .trialSession(trialSession)
                        .build();

                userRoleSessionRepository.save(userRoleSession);
            }
        }

        if (!isEmail) {
            List<String> lines = new ArrayList<>();
            String firstFormat = "%-" + longestEmail.length() + "s ";
            String secondFormat = "%-" + longestLogin.length() + "s ";

            String title = String.format(firstFormat + secondFormat + "%s\r\n", "E-mail", "Login", "Password");
            lines.add(title);

            emails.keySet().stream().forEach(key -> {
                String result = String.format(firstFormat + secondFormat + "%s\r\n", key, emails.get(key).get(0), emails.get(key).get(1));
                lines.add(result);
            });

            return lines;
        }

        return Collections.EMPTY_LIST;
    }

    private TrialUser getTrialUser(AuthUser authUser) {
        return TrialUser.builder().authUser(authUser)
                .userLanguage(Languages.ENGLISH)
                .isTrialCreator(true)
                .build();
    }

    private AuthUser createUser(UserForm user, String password, String prefix) throws Exception {
        String name = prefix + "_" + String.join("_", user.getRole());
        name = name.replaceAll(" ", "-");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AuthUser authUser = new AuthUser();

        authUser.setFirstName(name);
        authUser.setActivated(true);

        Example<AuthUser> example = Example.of(authUser);
        List<AuthUser> users = authUserRepository.findAll(example);
        String lastName = String.valueOf(users.size() + 1);

        authUser.setEmail(user.getEmail());
        authUser.setLogin(name + lastName);
        authUser.setLastName(lastName);
        authUser.setPassword(bCryptPasswordEncoder.encode(password));
        authUser.setCreatedAt(OffsetDateTime.now());

        AuthUserPosition authUserPosition = authUserPositionRepository.findAllByOrderByPositionAsc().stream()
                .filter(position -> position.getName().contains("User"))
                .findFirst()
                .orElse(null);

        authUser.setPosition(authUserPosition);
        authUser.setUnit(authUnitRepository.findOneCurrentlyAuthenticated().get());

        try {
            return authUserRepository.saveAndFlush(authUser);
        } catch (Exception e) {
            throw new Exception("User " + authUser.getEmail() + " already exists!: " + e.getMessage());
        }
    }

    public Map<Long, String> getTrials() {
        TrialUser trialUser = trialUserRepository.findByAuthUser(trialUserService.getCurrentUser());
        Map<Long, String> trialNames = new HashMap<>();

        for (TrialManager trialManager : trialUser.getTrialManagers()) {
            if (ManagementRoleType.SESSION_MANAGER.equals(trialManager.getManagementRole())) {
                trialNames.put(trialManager.getTrial().getId(), trialManager.getTrial().getName());
            }
        }

        return trialNames;
    }

    public JsonNode newSessionValues(long trialId) {
        List<TrialStage> trialStages = trialStageRepository.findAllByTrialId(trialId);
        List<TrialRole> trialRoles = trialRoleRepository.findAllByTrialId(trialId);
        List<AuthUser> authUsers = new ArrayList<>();

        for (AuthUser user : authUserRepository.findAll()) {
            for (AuthRole role : user.getRoles()) {
                if (AuthRoleType.ROLE_USER.name().contains(role.getShortName())) {
                    authUsers.add(user);
                    break;
                }
            }
        }

        authUsers.remove(trialUserService.getCurrentUser());
        return SchemaCreator.createNewSessionSchemaForm(trialStages, trialRoles, authUsers);
    }

    private Specifications<TrialSession> getTrialSessionStatusSpecifications(AuthUser authUser, SessionStatus sessionStatus) {
        Set<Specification<TrialSession>> conditions = new HashSet<>();
        conditions.add(TrialSessionSpecification.status(sessionStatus));
        conditions.add(TrialSessionSpecification.loggedUser(authUser));
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<TrialSession> getTrialSessionManagerSpecifications(AuthUser authUser) {
        Set<Specification<TrialSession>> conditions = new HashSet<>();
        conditions.add(TrialSessionSpecification.trialSessionManager(authUser));
        return RepositoryUtils.concatenate(conditions);
    }

    private Boolean setInitAnswer(TrialSessionDTO.ActiveListItem activeListItem, AuthUser authUser) {
        return activeListItem.initId != null ? answerService.hasAnswer(activeListItem.initId, authUser) : null;
    }

    @Scheduled(cron = "0/20 * * * * *")
    public void checkTrialStage() {
        RequestChangeOfTrialStage requestChangeOfTrialStage = getRequestChangeOfTrialStage();
        System.out.println("Receive Message from CheckTrialStage");

        if (requestChangeOfTrialStage != null) {
            long trialId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialId()).orElse(0);
            long trialSessionId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialSessionId()).orElse(0);
            long trialStageId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialStageId()).orElse(0);

            Optional<TrialSession> trialSession = trialSessionRepository.findByStatus(SessionStatus.ACTIVE);
            Trial trial;

            if (trialSession.isPresent()) {
                trial = trialSession.get().getTrial();
                Optional<TrialStage> trialStage = trialStageRepository.findByTrialIdAndTestBedStageId(trial.getId(), trialStageId);
                if (trialStage.isPresent()) {
                    trialSession.get().setLastTrialStage(trialStage.get());
                    trialSessionRepository.save(trialSession.get());
                } else {
                    System.out.println("Trial Stage does not exist");
                }
            } else {
                System.out.println("Trial Status with ACTIVE status does not exist");
            }

            List<ObservationType> listOfObservationType = observationTypeRepository.findAllByTrialIdAndTrialStageId(trialId, trialStageId);

            if (trialId == 0 || trialSessionId == 0 || trialStageId == 0) {
                for (ObservationType observationType : listOfObservationType) {
                    Optional<TrialSession> newTrialSession = trialSessionRepository.findById(trialSessionId);

                    if (trialSession.isPresent()) {
                        System.out.println("SendToTestBed");
                        answerRepository.findAllByTrialSessionIdAndObservationTypeId(trialSessionId, observationType.getId())
                                .forEach(answer -> sendToTestBed(answer, observationType, newTrialSession.get()));
                    }
                }
            } else {
                System.out.println("TrialId or TrailSessionId or TrialStageId is empty!");
            }
        }
    }

}
