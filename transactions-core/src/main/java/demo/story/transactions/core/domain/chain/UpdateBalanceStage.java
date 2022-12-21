package demo.story.transactions.core.domain.chain;

import demo.story.transactions.core.client.account.AccountCoreClient;
import demo.story.transactions.core.domain.Context;
import demo.story.transactions.core.exception.UpdateBalanceException;
import demo.story.transactions.core.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;

import static demo.story.transactions.core.representation.TransactionStatus.PENDING_SETTLEMENT;
import static demo.story.transactions.core.representation.TransactionStatus.WAITING_APPLY;
import static demo.story.transactions.core.util.TransactionUtil.stringTransactionToDecimal;

@Slf4j
@Component
public class UpdateBalanceStage extends Stage {

    private final AccountCoreClient accountCoreClient;
    private final TransactionRepository transactionRepository;

    public UpdateBalanceStage(AccountCoreClient accountCoreClient, TransactionRepository transactionRepository) {
        this.accountCoreClient = accountCoreClient;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean execute(Context context) {
        log.info("Executing stage update balance...");
        Long accountId = context.getRequest().getAccount();
        Long transactionId = context.getRequest().getId();
        BigDecimal amount = stringTransactionToDecimal(context.getRequest().getTransaction());

        try {
            accountCoreClient.updateBalance(accountId, amount);
        } catch (ResourceAccessException exception) {
            if (exception.getCause() instanceof HttpHostConnectException) {
                transactionRepository.updateStatus(transactionId, WAITING_APPLY);
            }

            if (exception.getCause() instanceof SocketTimeoutException) {
                transactionRepository.updateStatus(transactionId, PENDING_SETTLEMENT);
            }

            throw new UpdateBalanceException(exception.getMessage(), exception.getCause());
        }


        return executeNext(context);
    }
}
