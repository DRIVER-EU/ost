package pl.com.itti.app.driver.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialSession_;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.TrialStage_;
import pl.com.itti.app.driver.model.Trial_;

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
