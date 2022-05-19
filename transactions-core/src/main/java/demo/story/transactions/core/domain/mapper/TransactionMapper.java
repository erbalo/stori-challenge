package demo.story.transactions.core.domain.mapper;

import demo.story.transactions.core.entity.Transaction;
import demo.story.transactions.core.representation.TransactionRequest;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.representation.TransactionStatus.UNKNOWN;
import static demo.story.transactions.core.util.TransactionUtil.stringTransactionToDecimal;

@Component
public class TransactionMapper implements Mapper<TransactionRequest, Transaction> {

    @Override
    public Transaction fromDto(TransactionRequest request) {
        return Transaction.builder()
                .id(request.getId())
                .transaction(stringTransactionToDecimal(request.getTransaction()))
                .accountId(request.getAccount())
                .date(request.getDate())
                .origin(request.getOrigin())
                .reference(request.getReference())
                .status(UNKNOWN)
                .build();
    }

    @Override
    public TransactionRequest fromEntity(Transaction transaction) {
        return TransactionRequest.builder()
                .id(transaction.getId())
                .account(transaction.getAccountId())
                .date(transaction.getDate())
                .reference(transaction.getReference())
                .origin(transaction.getOrigin())
                .transaction(transaction.getTransaction().toString())
                .build();
    }
}
