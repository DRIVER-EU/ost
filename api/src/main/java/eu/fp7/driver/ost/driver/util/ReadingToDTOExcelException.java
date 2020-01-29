package eu.fp7.driver.ost.driver.util;

import eu.fp7.driver.ost.driver.model.enums.ErrorLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReadingToDTOExcelException extends RuntimeException {

  private ApiValidationError apiValidationError = new ApiValidationError();


  public ReadingToDTOExcelException(String message, ErrorLevel errorLevel) {
    super(message);
    apiValidationError.setMessage(message);
    apiValidationError.setErrorLevel(errorLevel);
  }

  public ApiValidationError getApiValidationError() {
    return apiValidationError;
  }


}
