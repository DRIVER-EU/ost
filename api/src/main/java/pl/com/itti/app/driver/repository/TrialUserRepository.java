package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.core.security.security.model.AuthUser;

import java.util.Optional;

public interface TrialUserRepository
        extends PagingAndSortingRepository<TrialUser, Long>, JpaSpecificationExecutor<TrialUser> {

    TrialUser findByAuthUser(AuthUser authUser);

    Optional<TrialUser> findById(Long id);
}
