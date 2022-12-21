package demo.story.transactions.core.consumer;

import demo.story.transactions.core.exception.RepeatedTransactionException;
import demo.story.transactions.core.exception.UpdateBalanceException;
import demo.story.transactions.core.representation.TransactionRequest;
import demo.story.transactions.core.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_DLQ_QUEUE;
import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_QUEUE;

@Slf4j
@Component
public class TransactionConsumer {

    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = NEW_TRANSACTION_QUEUE)
    public void handleMessage(TransactionRequest request) {
        try {
            log.info("********** Starting new pipeline transaction **********");
            log.info("{}", request);
            transactionService.create(request);
        } catch (RepeatedTransactionException | UpdateBalanceException exception) {
            log.error(exception.getMessage());
        }
        log.info("********** Finishing pipeline transaction **********");

    }

    @RabbitListener(queues = NEW_TRANSACTION_DLQ_QUEUE)
    public void test(Message message) {
        String jsonMessage = new String(message.getBody());
    }

}
