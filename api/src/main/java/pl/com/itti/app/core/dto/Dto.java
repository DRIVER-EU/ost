package pl.com.itti.app.core.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import pl.com.itti.app.core.dto.exception.DtoInstantiationException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class Dto {

    private static final Logger LOG = LoggerFactory.getLogger(Dto.class);

    private Dto() {
        throw new AssertionError();
    }

    /**
     * Converts a single entity into a DTO.
     *
     * @param sourceEntity object to be converted
     * @param destClass    target class (DTO)
     * @param <T>          type of a DTO
     * @param <E>          type of an entity
     * @return DTO of type {@code T}
     */
    public static <T extends EntityDto<E>, E> T from(E sourceEntity,
                                                     Class<T> destClass) {
        try {
            T instance = destClass.newInstance();
            instance.toDto(sourceEntity);
            return instance;
        } catch (NullPointerException | InstantiationException | IllegalAccessException e) {
            LOG.error("The requested resource cannot be converted to a DTO", e);
            throw new DtoInstantiationException(destClass, sourceEntity.getClass(), e);
        }
    }

    /**
     * Converts a list of entities into a list of DTOs.
     *
     * @param sourceEntities list of objects to be converted
     * @param destClass      target class (DTO)
     * @param <T>            type of a DTO
     * @param <E>            type of an entity
     * @return list with DTOs of type {@code T}
     */
    public static <T extends EntityDto<E>, E> List<T> from(List<E> sourceEntities,
                                                           Class<T> destClass) {
        return sourceEntities.stream()
                .map(o -> from(o, destClass))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Converts a set of entities into a set of DTOs.
     *
     * @param sourceEntities set of objects to be converted
     * @param destClass      target class (DTO)
     * @param <T>            type of a DTO
     * @param <E>            type of an entity
     * @return set with DTOs of type {@code T}
     */
    public static <T extends EntityDto<E>, E> Set<T> from(Set<E> sourceEntities,
                                                          Class<T> destClass) {
        return sourceEntities.stream()
                .filter(Objects::nonNull)
                .map(o -> from(o, destClass))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of entities into a sorted set of DTOs.
     *
     * @param sourceEntities set of objects to be converted
     * @param destClass      target class (DTO)
     * @param <T>            type of a DTO
     * @param <E>            type of an entity
     * @return set with DTOs of type {@code T}
     */
    public static <T extends EntityDto<E>, E> SortedSet<T> from(SortedSet<E> sourceEntities,
                                                                Class<T> destClass) {
        return sourceEntities.stream()
                .filter(Objects::nonNull)
                .map(o -> from(o, destClass))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Converts a page of entities into a page of DTOs.
     *
     * @param sourcePage page of objects to be converted
     * @param destClass  target class (DTO)
     * @param <T>        type of a DTO
     * @param <E>        type of an entity
     * @return list with DTOs of type {@code T}
     */
    public static <T extends EntityDto<E>, E> PageDto<T> from(Page<E> sourcePage,
                                                              Class<T> destClass) {
        return new PageDto<>(sourcePage.getTotalElements(), Dto.from(sourcePage.getContent(), destClass));
    }
}
