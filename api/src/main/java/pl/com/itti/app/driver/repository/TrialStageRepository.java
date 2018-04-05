package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialStage;

public interface TrialStageRepository extends PagingAndSortingRepository<TrialStage, Long>, JpaSpecificationExecutor {
}
