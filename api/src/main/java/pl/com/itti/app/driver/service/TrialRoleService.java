package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.TrialRoleSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

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
