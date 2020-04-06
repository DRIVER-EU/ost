package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSessionManager;
import eu.fp7.driver.ost.driver.model.TrialSessionManager_;
import eu.fp7.driver.ost.driver.model.TrialSession_;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.TrialUser_;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSession_;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialUserSpecification {

    public static Specification<TrialUser> trialSessionManager(AuthUser authUser, Long trialSessionId) {
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

    public static Specification<TrialUser> trialSessionUser(AuthUser authUser, Long trialSessionId) {
        if (authUser == null || trialSessionId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialUser, UserRoleSession> trialSessionManagerJoin = RepositoryUtils.getOrCreateJoin(root, TrialUser_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialSession> trialSessionJoin = trialSessionManagerJoin.join(UserRoleSession_.trialSession, JoinType.LEFT);
            return cb.and(
                    cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId),
                    cb.equal(root.get(TrialUser_.authUser), authUser)
            );
        };
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
