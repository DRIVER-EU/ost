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

    public static Specification<TrialUser> findByObserver(AuthUser authUser,
                                                          TrialSession trialSession,
                                                          ObservationType observationType) {
        if (authUser == null || trialSession == null || observationType == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialUser, UserRoleSession> userRoleSessionJoin = RepositoryUtils.getOrCreateJoin(root, TrialUser_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialRole> trialRoleJoin = userRoleSessionJoin.join(UserRoleSession_.trialRole, JoinType.LEFT);
            Join<TrialRole, TrialRole> roleJoin = trialRoleJoin.join(TrialRole_.trialRolesParents, JoinType.LEFT);

            Join<TrialRole, ObservationTypeTrialRole> observationTypeTrialRoleJoin = roleJoin.join(TrialRole_.observationTypeTrialRoles, JoinType.LEFT);

            Join<TrialRole, UserRoleSession> userRoleSessionJoinRole = roleJoin.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoinRole.join(UserRoleSession_.trialUser, JoinType.LEFT);

            return cb.and(
                    cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser),
                    cb.equal(userRoleSessionJoinRole.get(UserRoleSession_.trialSession), trialSession),
                    cb.equal(observationTypeTrialRoleJoin.get(ObservationTypeTrialRole_.observationType), observationType)
            );
        };
    }
}
