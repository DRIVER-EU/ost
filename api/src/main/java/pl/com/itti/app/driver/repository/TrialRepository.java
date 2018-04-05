package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.Trial;

public interface TrialRepository extends PagingAndSortingRepository<Trial, Long>, JpaSpecificationExecutor {
}
