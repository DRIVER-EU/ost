package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.repository.TrialRoleRepository;
import eu.fp7.driver.ost.driver.repository.TrialUserRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialRoleSpecification;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialRoleService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialUserService trialUserService;

    public Page<TrialRole> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);

        Set<Specification<TrialRole>> conditions = new HashSet<>();
        conditions.add(TrialRoleSpecification.trialRole(trialSessionId));
        return trialRoleRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }

    public Page<TrialRole> findByTrialId(Long trialId, Pageable pageable) {
        return trialRoleRepository.findAllByTrialId(trialId, pageable);
    }
}
