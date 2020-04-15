package Journalr.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
    
    /**
     * This method will recieve a message and then call the
     * super class runtime exception handling
     * @param message The message passed in from an exception
     */
    public MyFileNotFoundException(String message) {
        super(message);
    }

    /**
     * This method will recieve a message and Throwable and then
     * call the super class runtime exception handling to handle it
     * @param message The message of the exception
     * @param cause The throwable cause of the exception
     */
    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
