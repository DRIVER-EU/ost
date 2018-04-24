package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialStage;

import java.util.Optional;

public interface TrialStageRepository
        extends PagingAndSortingRepository<TrialStage, Long>, JpaSpecificationExecutor<TrialStage> {
    Optional<TrialStage> findById(long id);
}
