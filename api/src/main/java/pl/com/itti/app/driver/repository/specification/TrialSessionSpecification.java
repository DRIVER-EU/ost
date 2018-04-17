package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialSessionSpecification {

    public static Specification<TrialSession> status(SessionStatus sessionStatus) {
        return sessionStatus != null
                ? ((root, query, cb) -> cb.equal(root.get(TrialSession_.status), sessionStatus))
                : null;
    }

    public static Specification<TrialSession> loggedUser(AuthUser authUser) {
        Specification<TrialSession> specification = (root, query, cb) -> {
            Join<TrialSession, UserRoleSession> userRoleSessionJoin = root.join(TrialSession_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };

        return authUser != null ? specification : null;
    }
}
