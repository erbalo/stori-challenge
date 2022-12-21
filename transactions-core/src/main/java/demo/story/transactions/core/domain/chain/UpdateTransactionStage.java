package demo.story.transactions.core.domain.chain;

import demo.story.transactions.core.domain.Context;
import demo.story.transactions.core.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.representation.TransactionStatus.RECORDED;

@Slf4j
@Component
public class UpdateTransactionStage extends Stage {

    private final TransactionRepository transactionRepository;

    public UpdateTransactionStage(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean execute(Context context) {
        log.info("Executing stage update transaction...");
        Long transactionId = context.getRequest().getId();
        transactionRepository.updateStatus(transactionId, RECORDED);

        return executeNext(context);
    }

}
