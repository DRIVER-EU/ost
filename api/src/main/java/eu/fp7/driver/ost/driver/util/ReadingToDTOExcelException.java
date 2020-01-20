package eu.fp7.driver.ost.driver.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReadingToDTOExcelException extends RuntimeException {

  public ReadingToDTOExcelException(String message) {
    super(message);
  }
}
