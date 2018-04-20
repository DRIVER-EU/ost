package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.util.RepositoryUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialUserSpecification {

    public static Specification<TrialUser> authUser(AuthUser authUser, Long trialSessionId) {
        Specification<TrialUser> specification = (root, query, cb) -> {
            Join<TrialUser, TrialSessionManager> trialSessionManagerJoin = RepositoryUtils.getOrCreateJoin(root, TrialUser_.trialSessionManagers, JoinType.LEFT);
            Join<TrialSessionManager, TrialSession> trialSessionJoin = trialSessionManagerJoin.join(TrialSessionManager_.trialSession, JoinType.LEFT);
            return cb.and(
                    cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId),
                    cb.equal(root.get(TrialUser_.authUser), authUser)
            );
        };

        return authUser != null ? specification : null;
    }

    public static Specification<TrialUser> trialUsers(Long trialSessionId) {
        Specification<TrialUser> specification = (root, query, cb) -> {
            Join<TrialUser, UserRoleSession> userRoleSessionJoin = root.join(TrialUser_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialSession> trialSessionJoin = userRoleSessionJoin.join(UserRoleSession_.trialSession, JoinType.LEFT);
            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };

        return trialSessionId != null ? specification : null;
    }
}
