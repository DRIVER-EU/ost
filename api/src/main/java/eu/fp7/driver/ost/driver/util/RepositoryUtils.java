package eu.fp7.driver.ost.driver.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Set;

public final class RepositoryUtils {

    private RepositoryUtils() {
        throw new AssertionError();
    }

    /**
     * Concatenates conditions used to filter data
     *
     * @param conditions Set of specifications to filter data
     * @param <T>        Type of a filtered Entity
     * @return Specifications concatenated using AND operator
     */
    public static <T> Specifications<T> concatenate(Set<Specification<T>> conditions) {
        Specifications<T> specifications = Specifications.where(null);

        for (Specification<T> condition : conditions) {
            specifications = specifications.and(condition);
        }

        return specifications;
    }

    /**
     * Returns predicate TRUE = FALSE used to empty result
     * set when some of the conditions is not met.
     *
     * @param cb Criteria builder for the query
     * @return Bogus predicate
     */
    public static Predicate getBogusPredicate(CriteriaBuilder cb) {
        return cb.isTrue(cb.literal(false));
    }

    /**
     * @see #getOrCreateJoin(Root, SingularAttribute, JoinType)
     */
    public static <S, T> Join<S, T> getOrCreateJoin(final Root<S> root,
                                                    final SingularAttribute<? super S, T> attribute) {
        return getOrCreateJoin(root, attribute, JoinType.INNER);
    }

    /**
     * Gets an existing join or creates a new one if no match is found
     *
     * @param root      Source of the join
     * @param attribute Target of the join
     * @param joinType  Join type
     * @param <S>       The type containing the represented attribute
     * @param <T>       The type of the represented attribute
     * @return Join from S to T
     */
    public static <S, T> Join<S, T> getOrCreateJoin(final Root<S> root,
                                                    final SingularAttribute<? super S, T> attribute,
                                                    final JoinType joinType) {
        return root.getJoins()
                .stream()
                .filter(join -> join.getAttribute().equals(attribute) && join.getJoinType().equals(joinType))
                .findFirst()
                .map(join -> {
                    @SuppressWarnings("unchecked")
                    Join<S, T> unchecked = (Join<S, T>) join;
                    return unchecked;
                })
                .orElseGet(() -> root.join(attribute, joinType));
    }

    /**
     * Gets an existing join or creates a new one if no match is found
     *
     * @param root      Source of the join
     * @param attribute Target of the join
     * @param joinType  Join type
     * @param <S>       The type containing the represented attribute
     * @param <T>       The type of the represented attribute
     * @return Join from S to T
     */
    public static <S, T> Join<S, T> getOrCreateJoin(final Root<S> root,
                                                    final ListAttribute<? super S, T> attribute,
                                                    final JoinType joinType) {
        return root.getJoins()
                .stream()
                .filter(join -> join.getAttribute().equals(attribute) && join.getJoinType().equals(joinType))
                .findFirst()
                .map(join -> {
                    @SuppressWarnings("unchecked")
                    Join<S, T> unchecked = (Join<S, T>) join;
                    return unchecked;
                })
                .orElseGet(() -> root.join(attribute, joinType));
    }
}
