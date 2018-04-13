package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.util.RepositoryUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class UserRoleSessionSpecification {

    public static Specification<UserRoleSession> trailSessionStatus(SessionStatus sessionStatus) {
        Specification<UserRoleSession> specification = (root, query, cb) -> {
            Join<UserRoleSession, TrialSession> trialSessionJoin = RepositoryUtils.getOrCreateJoin(root, UserRoleSession_.trialSession, JoinType.LEFT);
            return cb.equal(trialSessionJoin.get(TrialSession_.status), sessionStatus);
        };

        return sessionStatus != null ? specification : null;
    }

    public static Specification<UserRoleSession> authUser(AuthUser authUser) {
        Specification<UserRoleSession> specification = (root, query, cb) -> {
            Join<UserRoleSession, TrialUser> trialUserJoin = root.join(UserRoleSession_.trialUser);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };

        return authUser != null ? specification : null;
    }
}
