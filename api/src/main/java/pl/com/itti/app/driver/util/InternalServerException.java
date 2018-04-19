package pl.com.itti.app.driver.util;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message, Throwable t) {
        super(message, t);
    }
}
