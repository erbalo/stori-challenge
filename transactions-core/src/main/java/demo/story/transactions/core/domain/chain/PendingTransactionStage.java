package demo.story.transactions.core.domain.chain;

import demo.story.transactions.core.domain.Context;
import demo.story.transactions.core.domain.mapper.TransactionMapper;
import demo.story.transactions.core.entity.Transaction;
import demo.story.transactions.core.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.representation.TransactionStatus.PENDING;

@Slf4j
@Component
public class PendingTransactionStage extends Stage {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public PendingTransactionStage(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public boolean execute(Context context) {
        log.info("Executing stage pending transaction...");
        Transaction transaction = transactionMapper.fromDto(context.getRequest());
        transaction = transaction.toBuilder()
                .status(PENDING)
                .build();
        transactionRepository.save(transaction);

        return executeNext(context);
    }
}
