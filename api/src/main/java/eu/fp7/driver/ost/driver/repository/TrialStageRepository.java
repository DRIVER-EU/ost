package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TrialStageRepository
        extends PagingAndSortingRepository<TrialStage, Long>, JpaSpecificationExecutor<TrialStage> {
    Optional<TrialStage> findById(long id);
    Optional<TrialStage> findByTrialIdAndName(long trialId, String name);
    Optional<TrialStage> findByTrialIdAndTestBedStageId(long trialId, long testBedStageId);
    Optional<TrialStage> findByIdAndTrialId(long id, long trialId);

    List<TrialStage> findAllByTrialId(long trialId);
    Page<TrialStage> findAllByTrialId(long trialId, Pageable pageable);
}
