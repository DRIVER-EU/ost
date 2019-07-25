package pl.com.itti.app.core.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import pl.com.itti.app.core.dto.exception.DtoInstantiationException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class Dto {

    private static final Logger LOG = LoggerFactory.getLogger(Dto.class);

    /**
     * Converts a single entity into a DTO.
     *
     * @param sourceEntity object to be converted
     * @param destClass    target class (DTO)
     * @param <T_Dest>     type of a DTO
     * @param <T_Entity>   type of an entity
     * @return DTO of type {@code T_Dest}
     */
    public static <T_Dest extends EntityDto<T_Entity>, T_Entity> T_Dest from(T_Entity sourceEntity,
                                                                             Class<T_Dest> destClass) {
        try {
            T_Dest instance = destClass.newInstance();
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
     * @param <T_Dest>       type of a DTO
     * @param <T_Entity>     type of an entity
     * @return list with DTOs of type {@code T_Dest}
     */
    public static <T_Dest extends EntityDto<T_Entity>, T_Entity> List<T_Dest> from(List<T_Entity> sourceEntities,
                                                                                   Class<T_Dest> destClass) {
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
     * @param <T_Dest>       type of a DTO
     * @param <T_Entity>     type of an entity
     * @return set with DTOs of type {@code T_Dest}
     */
    public static <T_Dest extends EntityDto<T_Entity>, T_Entity> Set<T_Dest> from(Set<T_Entity> sourceEntities,
                                                                                  Class<T_Dest> destClass) {
        return sourceEntities.stream()
                .filter(Objects::nonNull)
                .map(o -> from(o, destClass))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of entities into a tree set of DTOs.
     *
     * @param sourceEntities set of objects to be converted
     * @param destClass      target class (DTO)
     * @param <T_Dest>       type of a DTO
     * @param <T_Entity>     type of an entity
     * @return set with DTOs of type {@code T_Dest}
     */
    public static <T_Dest extends EntityDto<T_Entity>, T_Entity> TreeSet<T_Dest> from(TreeSet<T_Entity> sourceEntities,
                                                                                      Class<T_Dest> destClass) {
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
     * @param <T_Dest>   type of a DTO
     * @param <T_Entity> type of an entity
     * @return list with DTOs of type {@code T_Dest}
     */
    public static <T_Dest extends EntityDto<T_Entity>, T_Entity> PageDto<T_Dest> from(Page<T_Entity> sourcePage,
                                                                                      Class<T_Dest> destClass) {
        PageDto<T_Dest> pageDTO = new PageDto<>();
        pageDTO.setTotal(sourcePage.getTotalElements());
        pageDTO.setData(Dto.from(sourcePage.getContent(), destClass));
        return pageDTO;
    }

    private Dto() {
        throw new AssertionError();
    }
}
