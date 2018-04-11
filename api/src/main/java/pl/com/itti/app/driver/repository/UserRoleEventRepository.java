package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.UserRoleEvent;
import pl.com.itti.app.driver.model.UserRoleEventId;

import java.util.List;

public interface UserRoleEventRepository extends PagingAndSortingRepository<UserRoleEvent, UserRoleEventId>, JpaSpecificationExecutor {
    List<UserRoleEvent> findByEventId(long eventId);
}
