package pl.com.itti.app.driver.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcelImportException extends RuntimeException {

    public ExcelImportException(String message) {
        super(message);
    }
}
