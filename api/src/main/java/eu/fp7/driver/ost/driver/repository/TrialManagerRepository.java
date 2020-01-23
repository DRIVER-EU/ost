package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialManager;
import eu.fp7.driver.ost.driver.model.TrialManagerId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrialManagerRepository
        extends PagingAndSortingRepository<TrialManager, TrialManagerId>, JpaSpecificationExecutor<TrialManager> {
}
