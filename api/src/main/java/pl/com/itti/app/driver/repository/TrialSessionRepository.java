package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.TrialSession;

import java.util.Optional;

public interface TrialSessionRepository
        extends PagingAndSortingRepository<TrialSession, Long>, JpaSpecificationExecutor<TrialSession> {
    Optional<TrialSession> findById(long id);
 }
