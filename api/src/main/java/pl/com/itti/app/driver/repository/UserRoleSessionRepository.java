package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.model.UserRoleSessionId;

public interface UserRoleSessionRepository extends PagingAndSortingRepository<UserRoleSession, UserRoleSessionId>, JpaSpecificationExecutor {
}
