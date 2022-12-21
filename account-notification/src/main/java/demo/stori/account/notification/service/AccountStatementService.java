package demo.stori.account.notification.service;

import demo.stori.account.notification.client.ClientException;
import demo.stori.account.notification.client.account.AccountCoreClient;
import demo.stori.account.notification.client.statement.AccountStatementClient;
import demo.stori.account.notification.exception.ResourceNotProcessableException;
import demo.stori.account.notification.representation.AccountRepresentation;
import demo.stori.account.notification.representation.AccountStatementRepresentation;
import demo.stori.account.notification.representation.AccountStatementRequest;
import demo.stori.account.notification.representation.EmailStatementContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountStatementService {

    private final AccountCoreClient accountCoreClient;
    private final AccountStatementClient accountStatementClient;

    public AccountStatementService(AccountCoreClient accountCoreClient, AccountStatementClient accountStatementClient) {
        this.accountCoreClient = accountCoreClient;
        this.accountStatementClient = accountStatementClient;
    }

    public Optional<EmailStatementContext> retrieveStatement(AccountStatementRequest request) {
        Long accountId = request.getAccountId();
        try {
            AccountRepresentation account = accountCoreClient.getBalance(accountId);
            List<AccountStatementRepresentation> statements = accountStatementClient.getStatements(accountId, request.getYear());
            int statementsLen = statements.size();
            if (statementsLen == 0) {
                throw new ResourceNotProcessableException("Not possible to process empty statements");
            }

            log.info("Account {}", account);
            log.info("Statements size {}", statementsLen);

            EmailStatementContext context = EmailStatementContext.builder()
                    .request(request)
                    .account(account)
                    .statements(statements)
                    .build();
            return Optional.ofNullable(context);
        } catch (ClientException clientException) {
            log.error("Error {} with status {}", clientException.getMessage(), clientException.getStatusCode());
        }

        return Optional.empty();
    }
}
