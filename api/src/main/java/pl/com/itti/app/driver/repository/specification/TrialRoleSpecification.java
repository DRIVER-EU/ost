package pl.com.itti.app.driver.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialRoleSpecification {

    public static Specification<TrialRole> trialRole(Long trialSessionId) {
        Specification<TrialRole> specification = (root, query, cb) -> {
            Join<TrialRole, UserRoleSession> userRoleSessionJoin = root.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialSession> trialSessionJoin = userRoleSessionJoin.join(UserRoleSession_.trialSession, JoinType.LEFT);

            query.distinct(true);
            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };

        return trialSessionId != null ? specification : null;
    }
}
