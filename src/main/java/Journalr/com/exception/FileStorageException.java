package Journalr.com.exception;

public class FileStorageException extends RuntimeException {
    
    /**
     * This method will recieve a message and then call the
     * super class exception handling
     * @param message The message passed in from an exception
     */
    public FileStorageException(String message) {
        super(message);
    }

    /**
     * This method will recieve a message and Throwable and then
     * call the super class runtime exception handling to handle it
     * @param message The message of the exception
     * @param cause The throwable cause of the exception
     */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
