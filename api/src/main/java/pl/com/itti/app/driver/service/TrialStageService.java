package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.specification.TrialStageSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TrialStageService {

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public Page<TrialStage> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);

        Set<Specification<TrialStage>> conditions = new HashSet<>();
        conditions.add(TrialStageSpecification.trialStage(trialSessionId));

        return trialStageRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }
}
