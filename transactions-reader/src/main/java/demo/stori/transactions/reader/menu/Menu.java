package demo.stori.transactions.reader.menu;

import demo.stori.transactions.reader.domain.AccountNotificationDispatcher;
import demo.stori.transactions.reader.domain.TransactionCoreDispatcher;
import demo.stori.transactions.reader.representation.AccountStatementRequest;
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
    private final AccountNotificationDispatcher accountNotificationDispatcher;

    public Menu(FileService fileService, TransactionCoreDispatcher coreDispatcher, AccountNotificationDispatcher accountNotificationDispatcher) {
        this.fileService = fileService;
        this.coreDispatcher = coreDispatcher;
        this.accountNotificationDispatcher = accountNotificationDispatcher;
    }

    public void displayOptions() {
        System.out.println("\n ----------------------------------------------------------------");
        System.out.println("\t(P|p) Read CSV from giving path");
        System.out.println("\t(E|e) Request email for summary");
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
            case 'P':
            case 'p':
                return () -> {
                    System.out.print("[in] Enter the email when you'll receive the information $>> ");
                    String email = input.nextLine();
                    //String dir = System.getenv("MOUNT_DIRECTORY");
                    //String file = "11_2025_txns.csv";
                    System.out.print("[in] Enter the file path where we're going to receive the transactions $>> ");
                    String filePath = input.nextLine();
                    List<TransactionRequest> requests = fileService.processFile(filePath);
                    coreDispatcher.sendBulkTransactions(email, requests);
                };
            case 'E':
            case 'e':
                return () -> {
                    System.out.print("[in] Enter the email where you'll receive the information $>> ");
                    String email = input.nextLine();

                    System.out.print("[in] Enter the account from the statement that you want $>> ");
                    String stringAccount = input.nextLine();

                    System.out.print("[in] Enter the year from the statement that you want $>> ");
                    String stringYear = input.nextLine();

                    System.out.println("[out] Performing operation...");
                    AccountStatementRequest request = AccountStatementRequest.builder()
                            .toEmail(email)
                            .year(Integer.parseInt(stringYear))
                            .accountId(Long.parseLong(stringAccount))
                            .build();

                    accountNotificationDispatcher.emitStatementEmail(request);
                };
            case 'X':
            case 'x':
                return () -> {
                    System.out.println("[out] Exiting... thanks ;)");
                    System.exit(0);
                };
            default:
                throw new UnsupportedOperationException("[out] Type a valid option >:)");
        }
    }

}
