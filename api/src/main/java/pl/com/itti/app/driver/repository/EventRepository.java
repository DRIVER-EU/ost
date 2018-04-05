package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>, JpaSpecificationExecutor {
}
