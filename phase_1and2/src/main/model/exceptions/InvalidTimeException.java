package model.exceptions;

public class InvalidTimeException extends IllegalArgumentException {

    public InvalidTimeException() {
        super();
    }

    public InvalidTimeException(String msg) {
        super(msg);
    }
}
