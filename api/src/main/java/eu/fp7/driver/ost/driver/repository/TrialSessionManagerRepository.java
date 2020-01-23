package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialSessionManager;
import eu.fp7.driver.ost.driver.model.TrialSessionManagerId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrialSessionManagerRepository
        extends PagingAndSortingRepository<TrialSessionManager, TrialSessionManagerId>,
        JpaSpecificationExecutor<TrialSessionManager> {
}
