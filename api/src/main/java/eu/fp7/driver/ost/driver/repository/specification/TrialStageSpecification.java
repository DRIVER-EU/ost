package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSession_;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.TrialStage_;
import eu.fp7.driver.ost.driver.model.Trial_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialStageSpecification {

    public static Specification<TrialStage> trialStage(Long trialSessionId) {
        if (trialSessionId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialStage, Trial> trialJoin = root.join(TrialStage_.trial, JoinType.LEFT);
            Join<Trial, TrialSession> trialSessionJoin = trialJoin.join(Trial_.trialSessions, JoinType.LEFT);

            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };
    }
}
