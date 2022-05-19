package demo.stori.transactions.reader;

import demo.stori.transactions.reader.menu.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionsReaderApplication implements CommandLineRunner {

    private final Menu menu;

    public TransactionsReaderApplication(Menu menu) {
        this.menu = menu;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransactionsReaderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            try {
                menu.displayOptions();
                char choice = menu.getChoice();
                menu.perform(choice).execute();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
