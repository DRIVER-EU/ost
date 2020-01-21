package pl.com.itti.app.driver.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TrailDeleteException extends Throwable {
    public TrailDeleteException(String message, Exception e) {
        super(message, e);
        logIt(message + e.getMessage());
    }

    public TrailDeleteException(String message) {
        super(message);
        logIt(message);
    }


    private <T> void logIt(T element) {
        LogService.saveToLogFile(element.toString());
    }
}
