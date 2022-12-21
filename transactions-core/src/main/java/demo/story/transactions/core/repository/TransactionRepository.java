package demo.story.transactions.core.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import demo.story.transactions.core.entity.Transaction;
import demo.story.transactions.core.representation.TransactionStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public TransactionRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void save(Transaction transaction) {
        dynamoDBMapper.save(transaction);
    }

    public Optional<Transaction> getBy(Long transactionId) {
        return Optional.ofNullable(dynamoDBMapper.load(Transaction.class, transactionId));
    }

    public void updateStatus(Long transactionId, TransactionStatus status) {
        getBy(transactionId)
                .map(transaction -> transaction.toBuilder()
                        .status(status)
                        .build())
                .ifPresent(this::save);
    }
}
