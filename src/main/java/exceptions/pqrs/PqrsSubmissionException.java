package exceptions.pqrs;

public class PqrsSubmissionException extends RuntimeException {
    public PqrsSubmissionException(String message) {
        super(message);
    }
}
