package pl.com.itti.app.driver.service;

import co.perpixel.dto.PageDTO;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.TrialRoleSpecification;
import pl.com.itti.app.driver.repository.specification.TrialUserSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialRoleService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialRole> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.authUser(authUser, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new IllegalArgumentException("User is not TrialSessionManager of session: " + trialSessionId));

        Set<Specification<TrialRole>> conditions = new HashSet<>();
        conditions.add(TrialRoleSpecification.trialRole(trialSessionId));
        return trialRoleRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }
}
