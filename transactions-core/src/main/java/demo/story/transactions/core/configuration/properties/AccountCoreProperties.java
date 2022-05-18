package demo.story.transactions.core.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service.account-core")
public class AccountCoreProperties {

    public String baseURL;

}
