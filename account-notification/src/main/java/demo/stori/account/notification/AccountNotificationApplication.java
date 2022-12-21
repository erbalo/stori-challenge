package demo.stori.account.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class AccountNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountNotificationApplication.class, args);
    }

}
