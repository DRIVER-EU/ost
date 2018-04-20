package pl.com.itti.app.driver.util;

import org.everit.json.schema.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SchemaValidationException extends RuntimeException {

    public SchemaValidationException(ValidationException ve) {
        super(ve);
    }

    public SchemaValidationException(List<ValidationException> causingExceptions) {
        super(causingExceptions.stream().map(ValidationException::getErrorMessage).reduce((s, s2) -> s + s2).get());
    }
}
