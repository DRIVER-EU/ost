package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole_;
import eu.fp7.driver.ost.driver.model.ObservationType_;
import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSession_;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.TrialStage_;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.TrialUser_;
import eu.fp7.driver.ost.driver.model.Trial_;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSession_;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class ObservationTypeSpecification {

    public static Specification<ObservationType> trialSession(TrialSession trialSession) {
        if (trialSession == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<ObservationType, TrialStage> trialStageJoin = RepositoryUtils.getOrCreateJoin(root, ObservationType_.trialStage, JoinType.LEFT);
            Join<TrialStage, Trial> trialJoin = trialStageJoin.join(TrialStage_.trial, JoinType.LEFT);

            Join<Trial, TrialSession> sessionJoin = trialJoin.join(Trial_.trialSessions, JoinType.LEFT);

            return cb.and(
                    cb.isMember(trialSession, trialJoin.get(Trial_.trialSessions)),
                    cb.equal(sessionJoin.get(TrialSession_.lastTrialStage), trialSession.getLastTrialStage())
            );
        };
    }

    public static Specification<ObservationType> user(AuthUser authUser, TrialSession trialSession) {
        if (authUser == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<ObservationType, Trial> observationTypeTrialJoin = RepositoryUtils.getOrCreateJoin(root, ObservationType_.trial, JoinType.LEFT);
            Join<ObservationType, ObservationTypeTrialRole> observationTypeObservationTypeTrialRoleJoin = RepositoryUtils.getOrCreateJoin(root, ObservationType_.observationTypeTrialRoles, JoinType.LEFT);
            Join<Trial, TrialSession> trialTrialSessionJoin = observationTypeTrialJoin.join(Trial_.trialSessions, JoinType.LEFT);
            Join<TrialSession, UserRoleSession> trialSessionUserRoleSessionJoin = trialTrialSessionJoin.join(TrialSession_.userRoleSessions, JoinType.LEFT);

            Join<UserRoleSession, TrialUser> trialUserJoin = trialSessionUserRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);

            return cb.and(
                    cb.equal(trialSessionUserRoleSessionJoin.get(UserRoleSession_.trialSession), trialSession),
                    cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser),
                    cb.equal(root.get(ObservationType_.trialStage), trialSession.getLastTrialStage()),
                    cb.equal(trialSessionUserRoleSessionJoin.get(UserRoleSession_.trialRole), observationTypeObservationTypeTrialRoleJoin.get(ObservationTypeTrialRole_.trialRole))
            );
        };
    }
}
