package demo.stori.account.notification.client.account;

import demo.stori.account.notification.representation.AccountRepresentation;
import org.springframework.web.client.RestTemplate;

public class AccountClientImpl implements AccountCoreClient {

    private final RestTemplate restTemplate;

    public AccountClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AccountRepresentation getBalance(Long accountId) {
        String endpoint = "/accounts/" + accountId + "/balance";
        return restTemplate.getForObject(endpoint, AccountRepresentation.class);
    }
}
