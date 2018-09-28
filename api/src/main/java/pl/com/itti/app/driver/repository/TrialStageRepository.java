package pl.com.itti.app.driver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialStage;

import java.util.List;
import java.util.Optional;

public interface TrialStageRepository
        extends PagingAndSortingRepository<TrialStage, Long>, JpaSpecificationExecutor<TrialStage> {
    Optional<TrialStage> findById(long id);
    Optional<TrialStage> findByTrialIdAndName(long trialId, String name);

    List<TrialStage> findAllByTrialId(long trialId);
    Page<TrialStage> findAllByTrialId(long trialId, Pageable pageable);
}
