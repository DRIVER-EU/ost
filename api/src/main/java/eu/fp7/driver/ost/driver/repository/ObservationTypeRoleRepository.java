package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ObservationTypeRoleRepository
        extends PagingAndSortingRepository<ObservationTypeTrialRole, Long>, JpaSpecificationExecutor<ObservationTypeTrialRole> {

}
