package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
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
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public Page<TrialStage> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);
        return trialStageRepository.findAll(getTrialStageSessionSpecifications(trialSessionId), pageable);
    }

    @Transactional(readOnly = true)
    public Page<TrialStage> findByTrialId(Long trialId, Pageable pageable) {
        return trialStageRepository.findAllByTrialId(trialId, pageable);
    }

    private Specification<TrialStage> getTrialStageSessionSpecifications(Long trialSessionId) {
        Set<Specification<TrialStage>> conditions = new HashSet<>();
        conditions.add(TrialStageSpecification.trialStage(trialSessionId));
        return RepositoryUtils.concatenate(conditions);
    }
}
