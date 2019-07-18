package pl.com.itti.app.core.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "The requested resource cannot be converted to DTO")
public class DtoInstantiationException extends RuntimeException {

    private final Class<?> dtoClass;

    private final Class<?> entityClass;

    public DtoInstantiationException(Class<?> dtoClass, Class<?> entityClass, Throwable throwable) {
        super(throwable);
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public Class<?> getDtoClass() {
        return dtoClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
