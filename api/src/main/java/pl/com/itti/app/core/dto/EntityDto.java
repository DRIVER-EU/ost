package pl.com.itti.app.core.dto;

public interface EntityDto<T> {

    /**
     * Converts Entity into Data Transfer Object
     *
     * @param entity Entity to be converted
     */
    void toDto(T entity);
}
