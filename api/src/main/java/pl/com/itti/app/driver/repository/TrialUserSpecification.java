package pl.com.itti.app.driver.repository;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.util.RepositoryUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class TrialUserSpecification {

    public static Specification<TrialUser> findByObserver(AuthUser authUser,
                                                          TrialSession trialSession,
                                                          ObservationType observationType) {
        Specification<TrialUser> specification = (root, query, cb) -> {
            Join<TrialUser, UserRoleSession> userRoleSessionJoin = RepositoryUtils.getOrCreateJoin(root, TrialUser_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialRole> trialRoleJoin = userRoleSessionJoin.join(UserRoleSession_.trialRole, JoinType.LEFT);
            Join<TrialRole, TrialRole> roleJoin = trialRoleJoin.join(TrialRole_.trialRolesParents, JoinType.LEFT);

            Join<TrialRole, ObservationTypeTrialRole> observationTypeTrialRoleJoin = roleJoin.join(TrialRole_.observationTypeTrialRoles, JoinType.LEFT);

            Join<TrialRole, UserRoleSession> userRoleSessionJoin1 = roleJoin.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoin1.join(UserRoleSession_.trialUser, JoinType.LEFT);

            return cb.and(
                    cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser),
                    cb.equal(userRoleSessionJoin1.get(UserRoleSession_.trialSession), trialSession),
                    cb.equal(observationTypeTrialRoleJoin.get(ObservationTypeTrialRole_.observationType), observationType)
            );
        };

        return authUser != null ? specification : null;
    }
}
