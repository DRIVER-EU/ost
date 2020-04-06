package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.model.TrialUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrialUserRepository
        extends PagingAndSortingRepository<TrialUser, Long>, JpaSpecificationExecutor<TrialUser> {

    TrialUser findByAuthUser(AuthUser authUser);

    Optional<TrialUser> findById(Long id);

    Optional<TrialUser> getAllBy();
}
