package pl.com.itti.app.driver.repository.specification;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.util.RepositoryUtils;

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
            Join<Trial, TrialSession> trialTrialSessionJoin = observationTypeTrialJoin.join(Trial_.trialSessions, JoinType.LEFT);
            Join<TrialSession, UserRoleSession> trialSessionUserRoleSessionJoin = trialTrialSessionJoin.join(TrialSession_.userRoleSessions, JoinType.LEFT);

            Join<UserRoleSession, TrialUser> trialUserJoin = trialSessionUserRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);

            return cb.and(
                    cb.equal(trialSessionUserRoleSessionJoin.get(UserRoleSession_.trialSession), trialSession),
                    cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser),
                    cb.equal(trialTrialSessionJoin.get(TrialSession_.lastTrialStage), trialSession.getLastTrialStage())
            );
        };
    }
}
