package demo.stori.transactions.reader.menu;

import demo.stori.transactions.reader.domain.TransactionCoreDispatcher;
import demo.stori.transactions.reader.representation.TransactionRequest;
import demo.stori.transactions.reader.service.FileService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private static final Scanner input = new Scanner(System.in);

    private final FileService fileService;
    private final TransactionCoreDispatcher coreDispatcher;

    public Menu(FileService fileService, TransactionCoreDispatcher coreDispatcher) {
        this.fileService = fileService;
        this.coreDispatcher = coreDispatcher;
    }

    public void displayOptions() {
        System.out.println("\n ----------------------------------------------------------------");
        System.out.println("\t(T|t) Enter a transaction");
        System.out.println("\t(A|a) Show current account (The violations are not included)");
        System.out.println("\t(R|r) Reset");
        System.out.println("\t(X|x) Exit");
        System.out.println(" ----------------------------------------------------------------\n");
    }

    public char getChoice() {
        System.out.print("[in] Enter your selection $>> ");
        String choice = input.nextLine();
        char selection = ' ';

        if (choice.length() > 0) {
            selection = choice.charAt(0);
        }

        return selection;
    }

    public MenuOperation perform(char choice) {
        switch (choice) {
            case 'T':
            case 't':
                return () -> {
                    //System.out.print("[in] Enter the transaction $>> ");
                    //String json = input.nextLine();
                    String dir = System.getenv("MOUNT_DIRECTORY");
                    String file = "15_2022_txns.csv";
                    List<TransactionRequest> requests = fileService.processFile(dir + "/" + file);
                    coreDispatcher.sendBulkTransactions(requests);
                };
            case 'E':
            case 'e':

            case 'X':
            case 'x':
                return () -> {
                    System.out.println("Exiting... thanks ;)");
                    System.exit(0);
                };
            default:
                throw new UnsupportedOperationException("Type a valid option >:)");
        }
    }

}
