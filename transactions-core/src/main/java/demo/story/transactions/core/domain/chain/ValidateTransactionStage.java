package demo.story.transactions.core.domain.chain;

import demo.story.transactions.core.domain.Context;
import demo.story.transactions.core.exception.RepeatedTransactionException;
import demo.story.transactions.core.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateTransactionStage extends Stage {

    private final TransactionRepository transactionRepository;

    public ValidateTransactionStage(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean execute(Context context) {
        log.info("Executing stage validate transaction...");
        Long transactionId = context.getRequest().getId();
        transactionRepository.getBy(transactionId)
                .ifPresent(transactionStatus -> {
                    throw new RepeatedTransactionException("The transaction [" + transactionId + "] already exists");
                });

        return executeNext(context);
    }
}
