package demo.stori.account.core.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import demo.stori.account.core.entity.Account;
import demo.stori.account.core.representation.request.BalanceRequest;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class AccountRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public AccountRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void updateBalance(Long accountId, BalanceRequest request) {
        BigDecimal requestedAmount = request.getAmount();
        Account loaded = dynamoDBMapper.load(Account.class, accountId);

        Account accountToSave = Optional.ofNullable(loaded)
                .map(account -> {
                    BigDecimal current = account.getAmount();
                    return account.toBuilder()
                            .amount(current.add(requestedAmount))
                            .build();
                })
                .orElse(Account.builder()
                        .id(accountId)
                        .amount(requestedAmount)
                        .build());

        dynamoDBMapper.save(accountToSave);
    }

}
