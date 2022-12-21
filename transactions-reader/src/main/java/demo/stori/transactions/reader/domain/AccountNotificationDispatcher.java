package demo.stori.transactions.reader.domain;

import demo.stori.transactions.reader.representation.AccountStatementRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static demo.stori.transactions.reader.constants.RabbitConstants.ACCOUNT_STATEMENT_QUEUE;

@Component
public class AccountNotificationDispatcher {

    private final RabbitTemplate rabbitTemplate;

    public AccountNotificationDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void emitStatementEmail(AccountStatementRequest request) {
        rabbitTemplate.convertAndSend(ACCOUNT_STATEMENT_QUEUE, request);
    }
}
