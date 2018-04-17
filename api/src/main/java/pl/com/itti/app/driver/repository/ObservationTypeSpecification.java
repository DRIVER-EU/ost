package pl.com.itti.app.driver.repository;

import co.perpixel.security.model.AuthUser;
import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.util.RepositoryUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class ObservationTypeSpecification {

    public static Specification<ObservationType> trialSession(TrialSession trialSession) {
        Specification<ObservationType> specification = (root, query, cb) -> {
            Join<ObservationType, TrialStage> trialStageJoin = RepositoryUtils.getOrCreateJoin(root, ObservationType_.trialStage, JoinType.LEFT);
            Join<TrialStage, Trial> trialJoin = trialStageJoin.join(TrialStage_.trial, JoinType.LEFT);
            return cb.isMember(trialSession, trialJoin.get(Trial_.trialSessions));
        };

        return trialSession != null ? specification : null;
    }

    public static Specification<ObservationType> user(AuthUser authUser) {
        Specification<ObservationType> specification = (root, query, cb) -> {
            Join<ObservationType, ObservationTypeTrialRole> observationTypeTrialRoleJoin = RepositoryUtils.getOrCreateJoin(root, ObservationType_.observationTypeTrialRoles, JoinType.LEFT);
            Join<ObservationTypeTrialRole, TrialRole> trialRoleJoin = observationTypeTrialRoleJoin.join(ObservationTypeTrialRole_.trialRole, JoinType.LEFT);
            Join<TrialRole, UserRoleSession> userRoleSessionJoin = trialRoleJoin.join(TrialRole_.userRoleSessions, JoinType.LEFT);
            Join<UserRoleSession, TrialUser> trialUserJoin = userRoleSessionJoin.join(UserRoleSession_.trialUser, JoinType.LEFT);
            return cb.equal(trialUserJoin.get(TrialUser_.authUser), authUser);
        };

        return authUser != null ? specification : null;
    }
}
