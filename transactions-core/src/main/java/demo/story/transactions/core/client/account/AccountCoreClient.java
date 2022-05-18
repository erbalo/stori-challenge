package demo.story.transactions.core.client.account;

import java.math.BigDecimal;

public interface AccountCoreClient {

    public void updateBalance(Long accountId, BigDecimal amount);

}
