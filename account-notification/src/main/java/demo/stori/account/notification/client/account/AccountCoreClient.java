package demo.stori.account.notification.client.account;

import demo.stori.account.notification.representation.AccountRepresentation;

public interface AccountCoreClient {

    AccountRepresentation getBalance(Long accountId);

}
