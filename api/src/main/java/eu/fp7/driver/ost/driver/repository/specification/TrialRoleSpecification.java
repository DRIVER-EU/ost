package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole_;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.TrialRole_;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSession_;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.TrialUser_;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSession_;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.data.jpa.domain.Specification;

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

    public static Specification<TrialRole> findByObserver(AuthUser authUser,
                                                          TrialSession trialSession,
                                                          ObservationType observationType) {
        if (authUser == null || trialSession == null || observationType == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialRole, TrialRole> roleJoin = RepositoryUtils.getOrCreateJoin(root, TrialRole_.trialRolesParents, JoinType.LEFT);
            Join<TrialRole, ObservationTypeTrialRole> observationTypeTrialRoleJoin = roleJoin.join(TrialRole_.observationTypeTrialRoles, JoinType.LEFT);
            Join<TrialRole, UserRoleSession> userRoleSessionJoinRole = roleJoin.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoinRole.join(UserRoleSession_.trialUser, JoinType.LEFT);

            return cb.and(
                    cb.equal(observationTypeTrialRoleJoin.get(ObservationTypeTrialRole_.observationType), observationType),
                    cb.equal(userRoleSessionJoinRole.get(UserRoleSession_.trialSession), trialSession),
                    cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser)
            );
        };
    }

    public static Specification<TrialRole> trialSession(TrialSession trialSession) {
        if (trialSession == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<TrialRole, UserRoleSession> userRoleSessionJoin = RepositoryUtils.getOrCreateJoin(root, TrialRole_.userRoleSessions, JoinType.LEFT);
            query.distinct(true);
            return cb.equal(userRoleSessionJoin.get(UserRoleSession_.trialSession), trialSession);
        };
    }
}
