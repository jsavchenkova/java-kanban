package ya.tasktracker.exceptions;


public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
    }

    public ManagerSaveException(final Throwable cause) {
        super(cause);
    }

    public ManagerSaveException(String message, final Throwable cause) {
        super(message, cause);
    }
}
