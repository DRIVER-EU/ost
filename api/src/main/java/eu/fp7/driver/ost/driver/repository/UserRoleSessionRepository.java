package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSessionId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleSessionRepository
        extends PagingAndSortingRepository<UserRoleSession, UserRoleSessionId>,
        JpaSpecificationExecutor<UserRoleSession> {
    List<UserRoleSession>  findByTrialSession(TrialSession trialSession);
    Optional<UserRoleSession> findById(UserRoleSessionId id);
    List<UserRoleSession> findAllByOrderByTrialUser();
}
