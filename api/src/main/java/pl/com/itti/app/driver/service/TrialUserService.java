package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.TrialUserSpecification;
import pl.com.itti.app.driver.util.ForbiddenException;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialUserService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialUser> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = getCurrentUser();

        checkIsTrialSessionManager(authUser, trialSessionId);

        Set<Specification<TrialUser>> conditions = new HashSet<>();
        conditions.add(TrialUserSpecification.trialUsers(trialSessionId));
        return trialUserRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }

    public void checkIsTrialSessionManager(AuthUser userToCheck, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionManager(userToCheck, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionManager"));
    }

    public void checkHasTrialSession(AuthUser userToCheck, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionUser(userToCheck, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionUser"));
    }

    public AuthUser getCurrentUser() {
        return authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
    }
}
