package demo.stori.account.statement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import demo.stori.account.statement.entity.AccountStatement;
import demo.stori.account.statement.representation.AccountStatementReady;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class AccountStatementRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public AccountStatementRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void recordStatement(AccountStatementReady statementReady) {
        AccountStatement loaded = dynamoDBMapper.load(AccountStatement.class, statementReady.getDate(), statementReady.getAccountId());
        AccountStatement statementToSave = Optional.ofNullable(loaded)
                .map(statement -> {
                    Long currentQtyTransaction = statement.getTransactions();
                    BigDecimal currentDebit = statement.getDebit();
                    BigDecimal currentCredit = statement.getCredit();

                    return statement.toBuilder()
                            .debit(currentDebit.add(statementReady.getDebit()))
                            .credit(currentCredit.add(statementReady.getCredit()))
                            .transactions(currentQtyTransaction + 1)
                            .build();
                }).orElse(AccountStatement.builder()
                        .date(statementReady.getDate())
                        .transactions(1L)
                        .credit(statementReady.getCredit())
                        .debit(statementReady.getDebit())
                        .accountId(statementReady.getAccountId())
                        .build());

        dynamoDBMapper.save(statementToSave);
    }

}
