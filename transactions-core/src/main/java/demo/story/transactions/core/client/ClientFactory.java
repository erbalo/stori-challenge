package demo.story.transactions.core.client;

import demo.story.transactions.core.client.account.AccountClientImpl;
import demo.story.transactions.core.client.account.AccountCoreClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientFactory {

    private final RestTemplate accountCoreRestTemplate;

    public ClientFactory(RestTemplate accountCoreRestTemplate) {
        this.accountCoreRestTemplate = accountCoreRestTemplate;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public AccountCoreClient accountCoreClient() {
        return new AccountClientImpl(accountCoreRestTemplate);
    }

}
