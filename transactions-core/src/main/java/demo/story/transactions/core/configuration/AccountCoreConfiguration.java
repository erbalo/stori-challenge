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

@Configuration
public class AccountCoreConfiguration {

    private final AccountCoreProperties accountCoreProperties;

    public AccountCoreConfiguration(AccountCoreProperties accountCoreProperties) {
        this.accountCoreProperties = accountCoreProperties;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory accountCoreClientHttpFactory(){
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = "service.account-core.connection")
    public RestTemplate accountCoreRestTemplate(RestTemplateBuilder builder, ClientHttpRequestFactory accountCoreClientHttpFactory){
        return builder
                .requestFactory(accountCoreClientHttpFactory.getClass())
                .rootUri(accountCoreProperties.baseURL)
                .errorHandler(new ClientErrorHandler())
                .build();
    }

}
