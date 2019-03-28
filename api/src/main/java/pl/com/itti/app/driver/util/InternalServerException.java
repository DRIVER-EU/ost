package pl.com.itti.app.driver.util;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
