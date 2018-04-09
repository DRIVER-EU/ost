package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.util.RepositoryUtils;

import javax.persistence.criteria.Join;

public class ObservationTypeSpecification {

    public static Specification<ObservationType> trialSession(TrialSession trialSession) {
        Specification<ObservationType> specification = (root, query, cb) -> {
            Join<ObservationType, Trial> trialJoin = root.join(ObservationType_.trial);
            return cb.equal(trialJoin.get(Trial_.trialSessions), trialSession);
        };

        return trialSession != null ? specification : null;
    }
}
