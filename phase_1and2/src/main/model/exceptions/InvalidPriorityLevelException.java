package model.exceptions;

public class InvalidPriorityLevelException extends IllegalArgumentException {

    public InvalidPriorityLevelException() {
        super();
    }

    public InvalidPriorityLevelException(String msg) {
        super(msg);
    }
}
