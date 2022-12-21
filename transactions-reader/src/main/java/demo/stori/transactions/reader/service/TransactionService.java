package demo.stori.transactions.reader.service;

import demo.stori.transactions.reader.representation.AccountStatementRequest;
import demo.stori.transactions.reader.representation.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final ScheduleService scheduleService;

    public TransactionService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void prepareScheduleEmail(String email, List<TransactionRequest> requests) {
        Long account = detectAccount(requests);

        // Get all the years from the transactions
        Set<Integer> years = requests.stream()
                .map(TransactionRequest::getDate)
                .map(date -> {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);

                    return calendar.get(Calendar.YEAR);
                })
                .collect(Collectors.toSet());

        // Prepare the statements requests
        List<AccountStatementRequest> statementRequests = new ArrayList<>();
        years.stream()
                .map(year -> AccountStatementRequest.builder()
                        .accountId(account)
                        .toEmail(email)
                        .year(year)
                        .build())
                .forEach(statementRequests::add);

        statementRequests
                .forEach(scheduleService::scheduleTask);
    }

    private Long detectAccount(List<TransactionRequest> requests) {
        if (requests.size() == 0) {
            throw new RuntimeException("It's not possible schedule en email for empty transactions");
        }

        return requests.get(0).getAccount();
    }

}
