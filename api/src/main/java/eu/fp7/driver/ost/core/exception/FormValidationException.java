package eu.fp7.driver.ost.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FormValidationException extends RuntimeException {

    public FormValidationException(Class<?> entityClass) {
        super("Form '" + entityClass.getSimpleName() + "' contains invalid data");
    }
}
