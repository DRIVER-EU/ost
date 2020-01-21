package pl.com.itti.app.driver.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.com.itti.app.driver.model.enums.ErrorLevel;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationWarning {
//    private String object;
//    private String field;
//    private Object rejectedValue;
    private String message;
    private ErrorLevel errorLevel;
}
