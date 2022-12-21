package demo.stori.account.notification.consumer;

import demo.stori.account.notification.exception.ResourceNotProcessableException;
import demo.stori.account.notification.representation.AccountStatementRequest;
import demo.stori.account.notification.service.AccountStatementService;
import demo.stori.account.notification.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static demo.stori.account.notification.constants.RabbitConstants.ACCOUNT_STATEMENT_QUEUE;

@Slf4j
@Component
public class AccountStatementConsumer {

    private final AccountStatementService accountStatementService;
    private final EmailService emailService;

    public AccountStatementConsumer(AccountStatementService accountStatementService, EmailService emailService) {
        this.accountStatementService = accountStatementService;
        this.emailService = emailService;
    }

    @RabbitListener(queues = ACCOUNT_STATEMENT_QUEUE)
    public void handleMessage(AccountStatementRequest request) {
        log.info("********** Requesting new statement email **********");
        log.info("{}", request);
        try {
            accountStatementService.retrieveStatement(request)
                    .ifPresent(emailService::sendStatementTemplate);
        } catch (ResourceNotProcessableException exception) {
            log.error("Error sending email, details: {}", exception.getMessage());
        }
        log.info("********** Finishing sending email **********");
    }

}
