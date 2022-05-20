package demo.story.transactions.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class TransactionsCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsCoreApplication.class, args);
    }

}
