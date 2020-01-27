package eu.fp7.driver.ost.driver.service;


import com.fasterxml.jackson.databind.JsonNode;
import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.dto.PageDto;
import eu.fp7.driver.ost.core.exception.EntityNotFoundException;
import eu.fp7.driver.ost.core.security.security.model.AuthRole;
import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import eu.fp7.driver.ost.core.security.security.model.AuthUserPosition;
import eu.fp7.driver.ost.core.security.security.repository.AuthRoleRepository;
import eu.fp7.driver.ost.core.security.security.repository.AuthUnitRepository;
import eu.fp7.driver.ost.core.security.security.repository.AuthUserPositionRepository;
import eu.fp7.driver.ost.core.security.security.repository.AuthUserRepository;
import eu.fp7.driver.ost.driver.dto.AdminUserRoleDTO;
import eu.fp7.driver.ost.driver.dto.TrialSessionDTO;
import eu.fp7.driver.ost.driver.form.NewSessionForm;
import eu.fp7.driver.ost.driver.form.UserForm;
import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialManager;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSessionManager;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSessionId;
import eu.fp7.driver.ost.driver.model.enums.AuthRoleType;
import eu.fp7.driver.ost.driver.model.enums.Languages;
import eu.fp7.driver.ost.driver.model.enums.ManagementRoleType;
import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
import eu.fp7.driver.ost.driver.repository.AnswerRepository;
import eu.fp7.driver.ost.driver.repository.TrialRepository;
import eu.fp7.driver.ost.driver.repository.TrialRoleRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionManagerRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionRepository;
import eu.fp7.driver.ost.driver.repository.TrialStageRepository;
import eu.fp7.driver.ost.driver.repository.TrialUserRepository;
import eu.fp7.driver.ost.driver.repository.UserRoleSessionRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialSessionSpecification;
import eu.fp7.driver.ost.driver.util.InternalServerException;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import eu.fp7.driver.ost.driver.util.schema.SchemaCreator;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @Transactional()
    public TrialSession findById(long trialSessionId) {

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
    public PageDto<TrialSessionDTO.ActiveListItem> findByStatus(SessionStatus sessionStatus, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();

        Page<TrialSession> trialSessions = trialSessionRepository.findAll(
                getTrialSessionStatusSpecifications(authUser, sessionStatus),
                pageable);

        PageDto<TrialSessionDTO.ActiveListItem> pageDTO = Dto.from(trialSessions, TrialSessionDTO.ActiveListItem.class);
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

    public void delete(long trialSessionId) {
        Optional<TrialSession> trialSession = trialSessionRepository.findById(trialSessionId);
        if(!trialSession.isPresent()) {
            throw new EntityNotFoundException(TrialStage.class, trialSessionId);
        }
        trialSessionRepository.delete(trialSessionId);
    }

    public TrialSession update(TrialSessionDTO.AdminEditItem sessionDTO) {
        TrialSession trialSession = trialSessionRepository.findById(sessionDTO.id)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, sessionDTO.id));

        if(sessionDTO.lastTrialStageId !=0 ) {
            TrialStage trialStage = trialStageRepository.findById(sessionDTO.lastTrialStageId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, sessionDTO.getLastTrialStageId()));

            trialSession.setLastTrialStage(trialStage);
        }
        trialSession.setStatus(sessionDTO.status);
        return trialSessionRepository.save(trialSession);
    }

    public TrialSession insert(TrialSessionDTO.AdminEditItem sessionDTO) {

        TrialStage trialStage =null;
        if(sessionDTO.lastTrialStageId !=0 ) {
             trialStage = trialStageRepository.findById(sessionDTO.lastTrialStageId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, sessionDTO.getLastTrialStageId()));
        }
        Trial trial = trialRepository.findById(sessionDTO.trialId)
                .orElseThrow(() -> new EntityNotFoundException(Trial.class, sessionDTO.trialId));

        TrialSession trialSession = TrialSession.builder().trial(trial)
                .startTime(LocalDateTime.now())
                .status(SessionStatus.ACTIVE)
                .pausedTime(LocalDateTime.now())
                .lastTrialStage(trialStage)
                .build();

        return trialSessionRepository.save(trialSession);
    }

    public UserRoleSession insertUserRoleSession(AdminUserRoleDTO.FullItem  adminUserRoleDTO) {
        UserRoleSessionId userRoleSessionId =  new UserRoleSessionId();
        userRoleSessionId.setTrialRoleId(adminUserRoleDTO.getTrialRoleId());
        userRoleSessionId.setTrialSessionId(adminUserRoleDTO.getTrialSessionId());
        userRoleSessionId.setTrialUserId(adminUserRoleDTO.getTrialUserId());
        Optional<UserRoleSession> userRoleSessionExists = userRoleSessionRepository.findById(userRoleSessionId);

        if(userRoleSessionExists.isPresent()) {
            throw new InvalidDataException("User Session already exists. "+userRoleSessionId.toString());
        }
            TrialRole trialRole = trialRoleRepository.findById(adminUserRoleDTO.getTrialRoleId())
                .orElseThrow(() -> new EntityNotFoundException(TrialRole.class, adminUserRoleDTO.getTrialRoleId()));
            TrialSession trialSession =  trialSessionRepository.findById(adminUserRoleDTO.getTrialSessionId())
                    .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, adminUserRoleDTO.getTrialSessionId()));
            TrialUser trialUser = trialUserRepository.findById(adminUserRoleDTO.getTrialUserId())
                    .orElseThrow(() -> new EntityNotFoundException(TrialUser.class, adminUserRoleDTO.getTrialUserId()));
            UserRoleSession userRoleSession = UserRoleSession.builder()
                    .trialRole(trialRole)
                    .trialUser(trialUser)
                    .trialSession(trialSession)
                    .build();

            return userRoleSessionRepository.save(userRoleSession);

    }

    public void deleteUserRoleSession(UserRoleSessionId  userRoleSessionId) {
        UserRoleSession userRoleSession = userRoleSessionRepository.findById(userRoleSessionId)
                .orElseThrow(() -> new EntityNotFoundException(UserRoleSession.class, userRoleSessionId.getTrialUserId()));
        userRoleSessionRepository.delete(userRoleSession);
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
                    EmailService.sendNewSessionMail(authUser, password, trialName, user);
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
            for (String role : userForm.getRole()) {
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
        authUser.setCreatedAt(ZonedDateTime.now());

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

    public Trial getTrialByName(String trialName) {
        Trial trial = trialRepository.findByName(trialName).get();
        return trial;
    }

    public Trial getTrialById(long id) {
        Trial trial = trialRepository.findById(id).get();
        return trial;
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

    public String setManualStageChange(long sessionId, boolean isManual){
        TrialSession trialSession = trialSessionRepository.findById(sessionId).get();
        if(trialSession==null)return "no session found ";
        trialSession.setIsManualStageChange(isManual);
        trialSessionRepository.save(trialSession);
        return "current stage in session " +trialSession.getId() + " is: " +trialSession.getLastTrialStage().getId() + "/"+trialSession.getLastTrialStage().getName()
                + "  manual mode is " +isManual;
    }

}
