package demo.story.transactions.core.configuration;

import demo.story.transactions.core.client.ClientErrorHandler;
import demo.story.transactions.core.configuration.properties.AccountCoreProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AccountCoreConfiguration {

    private final AccountCoreProperties accountCoreProperties;

    public AccountCoreConfiguration(AccountCoreProperties accountCoreProperties) {
        this.accountCoreProperties = accountCoreProperties;
    }

    @Bean
    @ConfigurationProperties(prefix = "service.account-core.connection")
    public HttpComponentsClientHttpRequestFactory accountCoreClientHttpFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate accountCoreRestTemplate(ClientHttpRequestFactory accountCoreClientHttpFactory) {
        RestTemplate restTemplate = new RestTemplate(accountCoreClientHttpFactory);
        restTemplate.setErrorHandler(new ClientErrorHandler());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(accountCoreProperties.getBaseURL()));
        return restTemplate;
    }

}
