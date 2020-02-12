package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TrialSessionRepository
        extends PagingAndSortingRepository<TrialSession, Long>, JpaSpecificationExecutor<TrialSession> {
    Optional<TrialSession> findById(long id);

    Optional<TrialSession> findByIdAndTrialId(long id, long trialId);

    List<TrialSession> findByStatus(SessionStatus status);

    Optional<TrialSession> findByIdAndStatus(long id, SessionStatus status);


}
