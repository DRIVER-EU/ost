package eu.fp7.driver.ost.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, long entityId) {
        super("Requested entity '" + entityClass.getSimpleName() + "' with id '" + entityId + "' was not found");
    }

    public EntityNotFoundException(Class<?> entityClass) {
        super("Requested entity '" + entityClass.getSimpleName() + "' was not found");
    }
}
