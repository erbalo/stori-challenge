package demo.story.transactions.core.exception;

public class RepeatedTransactionException extends RuntimeException {

    public RepeatedTransactionException(String message) {
        super(message);
    }

}
