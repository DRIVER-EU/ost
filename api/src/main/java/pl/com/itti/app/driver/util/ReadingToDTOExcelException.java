package pl.com.itti.app.driver.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.itti.app.driver.model.enums.ErrorLevel;

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
