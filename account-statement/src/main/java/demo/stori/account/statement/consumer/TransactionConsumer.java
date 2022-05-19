package demo.stori.account.statement.consumer;

import demo.stori.account.statement.representation.TransactionRequest;
import demo.stori.account.statement.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static demo.stori.account.statement.constants.RabbitConstants.NEW_TRANSACTION_QUEUE;

@Slf4j
@Component
public class TransactionConsumer {

    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = NEW_TRANSACTION_QUEUE)
    public void handleMessage(TransactionRequest request) {

    }

}
