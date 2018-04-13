package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;

import javax.persistence.criteria.Join;

public class TrialSessionManagerSpecification {

    public static Specification<TrialSessionManager> authUser(AuthUser authUser) {
        Specification<TrialSessionManager> specification = (root, query, cb) -> {
            Join<TrialSessionManager, TrialUser> trialUserJoin = root.join(TrialSessionManager_.trialUser);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };

        return authUser != null ? specification : null;
    }

    public static Specification<TrialSessionManager> trialSessionManager(Long trialSessionId) {
        Specification<TrialSessionManager> specification = (root, query, cb) -> {
            Join<TrialSessionManager, TrialSession> trialSessionJoin = root.join(TrialSessionManager_.trialSession);
            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };

        return trialSessionId != null ? specification : null;
    }
}
