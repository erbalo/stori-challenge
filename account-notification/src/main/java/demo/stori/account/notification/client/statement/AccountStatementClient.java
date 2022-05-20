package demo.stori.account.notification.client.statement;

import demo.stori.account.notification.representation.AccountStatementRepresentation;

import java.util.List;

public interface AccountStatementClient {

    List<AccountStatementRepresentation> getStatements(Long accountId, int year);

}
