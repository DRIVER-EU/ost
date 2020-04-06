package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.model.Event;
import eu.fp7.driver.ost.driver.model.Event_;
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

public class EventSpecification {

    public static Specification<Event> isConnectedToAuthUser(AuthUser authUser) {
        if (authUser == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Event, TrialUser> trial = RepositoryUtils.getOrCreateJoin(root, Event_.trialUser, JoinType.LEFT);

            Join<Event, TrialRole> trialRoleJoin = RepositoryUtils.getOrCreateJoin(root, Event_.trialRole, JoinType.LEFT);
            Join<TrialRole, UserRoleSession> userRoleSessionJoin = trialRoleJoin.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserByRoleJoin = userRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);


            return cb.or(
                    cb.and(
                            cb.isNull(root.get(Event_.trialUser)),
                            cb.isNull(root.get(Event_.trialRole))
                    ),
                    cb.equal(trial.get(TrialUser_.authUser), authUser),
                    cb.equal(trialUserByRoleJoin.get(TrialUser_.authUser), authUser)
            );
        };
    }

    public static Specification<Event> inTrialSession(Long trialSessionId) {
        if (trialSessionId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Event, TrialSession> trialSessionJoin = RepositoryUtils.getOrCreateJoin(root, Event_.trialSession, JoinType.LEFT);

            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };
    }
}
