package demo.stori.account.notification.configuration;

import demo.stori.account.notification.client.ClientErrorHandler;
import demo.stori.account.notification.configuration.properties.AccountStatementProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AccountStatementConfiguration {

    private final AccountStatementProperties accountStatementProperties;

    public AccountStatementConfiguration(AccountStatementProperties accountStatementProperties) {
        this.accountStatementProperties = accountStatementProperties;
    }

    @Bean
    @ConfigurationProperties(prefix = "service.account-statement.connection")
    public HttpComponentsClientHttpRequestFactory accountStatementClientHttpFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate accountStatementRestTemplate(ClientHttpRequestFactory accountStatementClientHttpFactory) {
        RestTemplate restTemplate = new RestTemplate(accountStatementClientHttpFactory);
        restTemplate.setErrorHandler(new ClientErrorHandler());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(accountStatementProperties.getBaseURL()));
        return restTemplate;
    }
}
