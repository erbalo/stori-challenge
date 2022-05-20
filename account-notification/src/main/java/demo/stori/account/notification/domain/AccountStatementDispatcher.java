package demo.stori.account.notification.domain;

import demo.stori.account.notification.representation.AccountStatementRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static demo.stori.account.notification.constants.RabbitConstants.ACCOUNT_STATEMENT_QUEUE;

@Component
public class AccountStatementDispatcher {

    private final RabbitTemplate rabbitTemplate;

    public AccountStatementDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void queueStatementRequest(AccountStatementRequest request) {
        rabbitTemplate.convertAndSend(ACCOUNT_STATEMENT_QUEUE, request);
    }

}
