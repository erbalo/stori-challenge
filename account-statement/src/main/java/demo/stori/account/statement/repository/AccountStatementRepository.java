package demo.stori.account.statement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import demo.stori.account.statement.entity.AccountStatement;
import demo.stori.account.statement.representation.AccountStatementReady;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<AccountStatement> getStatementBy(Long accountId, Integer year) {
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#accountId", "account_id");
        expressionAttributesNames.put("#stamentDate", "date");

        Map<String, AttributeValue> expressionAttributes = new HashMap<>();
        expressionAttributes.put(":account", new AttributeValue().withN(accountId.toString()));
        expressionAttributes.put(":year", new AttributeValue().withS(year.toString()));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withConsistentRead(false)
                .withIndexName("account-id-idx")
                .withFilterExpression("#accountId = :account AND begins_with(#stamentDate, :year)")
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributes);

        return dynamoDBMapper.scan(AccountStatement.class, queryExpression);
    }

}
