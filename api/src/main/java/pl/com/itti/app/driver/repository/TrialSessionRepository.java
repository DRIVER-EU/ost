package pl.com.itti.app.driver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import java.util.List;

public interface TrialSessionRepository extends PagingAndSortingRepository<TrialSession, Long>, JpaSpecificationExecutor {
    List<TrialSession> findByStatus(SessionStatus sessionStatus, Pageable pageable);

}
