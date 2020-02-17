package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRoleId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ObservationTypeRoleRepository
        extends PagingAndSortingRepository<ObservationTypeTrialRole, Long>, JpaSpecificationExecutor<ObservationTypeTrialRole> {

    Optional<ObservationTypeTrialRole> findObservationTypeTrialRoleById(ObservationTypeTrialRoleId observationTypeTrialRoleId);

}
