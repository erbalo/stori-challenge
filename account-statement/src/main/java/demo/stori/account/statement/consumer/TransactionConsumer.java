package demo.stori.account.statement.consumer;

import demo.stori.account.statement.representation.TransactionRequest;
import demo.stori.account.statement.services.AccountStatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import static demo.stori.account.statement.constants.RabbitConstants.NEW_TRANSACTION_QUEUE;

@Slf4j
@Component
public class TransactionConsumer {

    private final AccountStatementService accountStatementService;

    public TransactionConsumer(AccountStatementService accountStatementService) {
        this.accountStatementService = accountStatementService;
    }

    @RabbitListener(queues = NEW_TRANSACTION_QUEUE)
    public void handleMessage(TransactionRequest request) throws ParseException {
        log.info("********** Receiving transaction request **********");
        log.info("{}", request);
        accountStatementService.create(request);
        log.info("********** Finishing account statement for transaction **********");
    }
}
