package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Page<Event> findAllByTrialSessionId(long trialSessionId, Pageable pageable);

    List<Event> findAllByTrialSessionId(long trialSessionId);
}
