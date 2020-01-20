package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSessionId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRoleSessionRepository
        extends PagingAndSortingRepository<UserRoleSession, UserRoleSessionId>,
        JpaSpecificationExecutor<UserRoleSession> {
    List<UserRoleSession>  findByTrialSession(TrialSession trialSession);
}
