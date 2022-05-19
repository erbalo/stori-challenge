package demo.story.transactions.core.domain.dispatcher;

import demo.story.transactions.core.representation.TransactionRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_ACCOUNT_STATEMENT_QUEUE;

@Component
public class AccountStatementDispatcher {

    private final RabbitTemplate rabbitTemplate;

    public AccountStatementDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void replicateTransaction(TransactionRequest request) {
        rabbitTemplate.convertAndSend(NEW_TRANSACTION_ACCOUNT_STATEMENT_QUEUE, request);
    }
}
