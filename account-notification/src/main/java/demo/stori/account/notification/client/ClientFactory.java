package demo.stori.account.notification.client;

import demo.stori.account.notification.client.account.AccountClientImpl;
import demo.stori.account.notification.client.account.AccountCoreClient;
import demo.stori.account.notification.client.statement.AccountStatementClient;
import demo.stori.account.notification.client.statement.AccountStatementClientImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientFactory {

    private final RestTemplate accountCoreRestTemplate;
    private final RestTemplate accountStatementRestTemplate;

    public ClientFactory(RestTemplate accountCoreRestTemplate, RestTemplate accountStatementRestTemplate) {
        this.accountCoreRestTemplate = accountCoreRestTemplate;
        this.accountStatementRestTemplate = accountStatementRestTemplate;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public AccountCoreClient accountCoreClient() {
        return new AccountClientImpl(accountCoreRestTemplate);
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public AccountStatementClient accountStatementClient() {
        return new AccountStatementClientImpl(accountStatementRestTemplate);
    }

}
