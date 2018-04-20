package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialSessionSpecification {

    public static Specification<TrialSession> status(SessionStatus sessionStatus) {
        if (sessionStatus == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get(TrialSession_.status), sessionStatus);
    }

    public static Specification<TrialSession> loggedUser(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialSession, UserRoleSession> userRoleSessionJoin = root.join(TrialSession_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };
    }

    public static Specification<TrialSession> trialSessionManager(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialSession, TrialSessionManager> trialSessionManagerJoin = root.join(TrialSession_.trialSessionManagers, JoinType.LEFT);
            Join<TrialSessionManager, TrialUser> trialUserJoin = trialSessionManagerJoin.join(TrialSessionManager_.trialUser, JoinType.LEFT);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };
    }
}
