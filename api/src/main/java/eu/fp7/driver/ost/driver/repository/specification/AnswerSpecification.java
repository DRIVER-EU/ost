package eu.fp7.driver.ost.driver.repository.specification;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.Answer_;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.ObservationType_;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSession_;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.TrialUser_;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.keycloak.KeycloakPrincipal;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;

public class AnswerSpecification {

    public static Specification<Answer> isConnectedToAuthUser(String keycloakUserId) {
        if (keycloakUserId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Answer, TrialUser> trialUserJoin = RepositoryUtils.getOrCreateJoin(root, Answer_.trialUser, JoinType.LEFT);

            return cb.equal(trialUserJoin.get(TrialUser_.keycloakUserId), keycloakUserId);
        };
    }

    public static Specification<Answer> inTrialSession(Long trialSessionId) {
        if (trialSessionId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Answer, TrialSession> trialSessionJoin = RepositoryUtils.getOrCreateJoin(root, Answer_.trialSession, JoinType.LEFT);

            return cb.equal(trialSessionJoin.get(TrialSession_.id), trialSessionId);
        };
    }

    public static Specification<Answer> isAnswerForObservationType(Long observationTypeId) {
        if (observationTypeId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Answer, ObservationType> observationTypeJoin = RepositoryUtils.getOrCreateJoin(root, Answer_.observationType, JoinType.LEFT);

            return cb.equal(observationTypeJoin.get(ObservationType_.id), observationTypeId);
        };
    }

    public static Specification<Answer> inLastTrialStage(Long trialSessionId) {
        if (trialSessionId == null) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Answer, TrialSession> trialSessionJoin = RepositoryUtils.getOrCreateJoin(root, Answer_.trialSession, JoinType.LEFT);
            Path<TrialStage> lastTrialStage = trialSessionJoin.get(TrialSession_.lastTrialStage);

            Join<Answer, ObservationType> observationTypeJoin = RepositoryUtils.getOrCreateJoin(root, Answer_.observationType, JoinType.LEFT);
            Path<TrialStage> actualTrialStage = observationTypeJoin.get(ObservationType_.trialStage);

            return cb.or(
                    cb.equal(lastTrialStage, actualTrialStage),
                    cb.isNull(actualTrialStage));
        };
    }

    public static Specification<Answer> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get(Answer_.deleteComment));
    }
}
