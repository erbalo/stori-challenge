package demo.stori.transactions.reader.service;

import demo.stori.transactions.reader.domain.AccountNotificationDispatcher;
import demo.stori.transactions.reader.representation.AccountStatementRequest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ScheduleService {

    private final TaskScheduler taskScheduler;
    private final AccountNotificationDispatcher accountNotificationDispatcher;

    public ScheduleService(TaskScheduler taskScheduler, AccountNotificationDispatcher accountNotificationDispatcher) {
        this.taskScheduler = taskScheduler;
        this.accountNotificationDispatcher = accountNotificationDispatcher;
    }

    @Async
    public void scheduleTask(AccountStatementRequest request) {
        Instant now = new Date().toInstant();
        Instant secs = now.plusSeconds(30);
        taskScheduler.schedule(() -> accountNotificationDispatcher.emitStatementEmail(request), secs);
    }

}
