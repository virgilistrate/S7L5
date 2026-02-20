package virgilistrate.S7L5.exceptions;

public class UnautorizedException extends RuntimeException {
    public UnautorizedException(String message) {
        super(message);
    }
}