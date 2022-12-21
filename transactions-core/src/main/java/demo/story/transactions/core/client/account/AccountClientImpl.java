package demo.story.transactions.core.client.account;

import demo.story.transactions.core.client.ClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountClientImpl implements AccountCoreClient {

    private final RestTemplate restTemplate;

    public AccountClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void updateBalance(Long accountId, BigDecimal amount) {
        String endpoint = "/accounts/" + accountId + "/balance";

        HashMap<String, BigDecimal> payload = new HashMap<>();
        payload.put("amount", amount);
        restTemplate.put(endpoint, payload);
    }
}
