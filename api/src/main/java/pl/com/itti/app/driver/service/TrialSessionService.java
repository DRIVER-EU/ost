package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthRole;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.AuthRoleType;
import pl.com.itti.app.driver.model.enums.ManagementRoleType;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.TrialSessionSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.util.*;

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
    private TrialUserRepository trialUserRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

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

    public void changeStatus(long answerId, String status) {
        Optional<TrialSession> trialSession = trialSessionRepository.findById(answerId);

        if (trialSession.isPresent()) {
            SessionStatus sessionStatus = SessionStatus.valueOf(status);
            trialSession.get().setStatus(sessionStatus);
            trialSessionRepository.save(trialSession.get());
        }
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
