package eu.fp7.driver.ost.driver.util;

import org.everit.json.schema.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SchemaValidationException extends RuntimeException {

    public SchemaValidationException(ValidationException ve) {
        super(ve);
    }
}
