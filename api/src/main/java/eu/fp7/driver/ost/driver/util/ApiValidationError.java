package eu.fp7.driver.ost.driver.util;

import eu.fp7.driver.ost.driver.model.enums.ErrorLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError {
//    private String object;
//    private String field;
//    private Object rejectedValue;
    private String message;
    private ErrorLevel errorLevel;

    public ApiValidationError() {

    }
}
