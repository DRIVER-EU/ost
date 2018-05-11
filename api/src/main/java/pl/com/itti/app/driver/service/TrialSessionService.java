package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.specification.TrialSessionSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TrialSessionService {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public TrialSession findOneByManager(long trialSessionId) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);

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
    public Page<TrialSession> findByStatus(SessionStatus sessionStatus, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();

        return trialSessionRepository.findAll(
                getTrialSessionStatusSpecifications(authUser, sessionStatus),
                pageable
        );
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

    public
}
