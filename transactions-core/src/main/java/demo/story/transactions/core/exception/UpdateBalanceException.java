package demo.story.transactions.core.exception;

public class UpdateBalanceException extends RuntimeException {
    public UpdateBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
