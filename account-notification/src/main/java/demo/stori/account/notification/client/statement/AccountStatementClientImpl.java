package demo.stori.account.notification.client.statement;

import demo.stori.account.notification.representation.AccountStatementRepresentation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountStatementClientImpl implements AccountStatementClient {

    private final RestTemplate restTemplate;

    public AccountStatementClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<AccountStatementRepresentation> getStatements(Long accountId, int year) {
        String endpoint = "/account-statements/" + accountId + "?year=" + year;

        ResponseEntity<List<AccountStatementRepresentation>> response =
                restTemplate.exchange(endpoint,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountStatementRepresentation>>() {
                        });

        return response.getBody();
    }
}
