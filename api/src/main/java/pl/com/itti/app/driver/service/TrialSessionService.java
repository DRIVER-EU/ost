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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.TrialSessionSpecification;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;
import java.util.*;
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
    private ObservationTypeService observationTypeService;

    @Autowired
    private AnswerService answerService;

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

    public void createNewSession(NewSessionForm newSessionForm) {
        newSessionForm.getUsers().forEach(user ->  {
            try {
                String password = UUID.randomUUID().toString();
                AuthUser authUser = createUser(user, password, newSessionForm.prefix);
                trialUserRepository.save(getTrialUser(authUser));
                AuthRole authRole = StreamSupport.stream(authRoleRepository.findAll().spliterator(), false)
                        .filter(role -> role.getShortName().contains("ROLE_USER"))
                        .findFirst()
                        .orElse(null);

                authUser.setRoles(Stream.of(authRole).collect(Collectors.toSet()));
                EmailService.send(authUser, password, newSessionForm.getTrialName(), user);
            } catch (MessagingException e) {
                throw new InternalServerException("Cannot crate new user: " + e.getMessage());
            }
        });
    }

    private TrialUser getTrialUser(AuthUser authUser) {
        return TrialUser.builder().authUser(authUser)
                .userLanguage(Languages.ENGLISH)
                .isTrialCreator(true)
                .build();
    }

    private AuthUser createUser(UserForm user, String password, String prefix) {
        String name = prefix + "_" + String.join("_", user.getRole());
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

        return authUserRepository.saveAndFlush(authUser);
    }

    public List<String> getTrials() {
        TrialUser trialUser = trialUserRepository.findByAuthUser(trialUserService.getCurrentUser());
        List<String> trialNames = new ArrayList<>();

        for (TrialManager trialManager : trialUser.getTrialManagers()) {
            if (ManagementRoleType.SESSION_MANAGER.equals(trialManager.getManagementRole())) {
                trialNames.add(trialManager.getTrial().getName());
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
}
