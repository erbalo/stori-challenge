package demo.story.transactions.core.service;

import demo.story.transactions.core.domain.Context;
import demo.story.transactions.core.domain.StageFactory;
import demo.story.transactions.core.domain.chain.Stage;
import demo.story.transactions.core.domain.dispatcher.AccountStatementDispatcher;
import demo.story.transactions.core.representation.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    private final StageFactory stageFactory;
    private final AccountStatementDispatcher accountStatementDispatcher;

    public TransactionService(StageFactory stageFactory, AccountStatementDispatcher accountStatementDispatcher) {
        this.stageFactory = stageFactory;
        this.accountStatementDispatcher = accountStatementDispatcher;
    }

    public void create(TransactionRequest request) {
        Context context = Context.builder()
                .request(request)
                .build();

        Stage pipeline = stageFactory.transactionPipeline();
        pipeline.execute(context);
        accountStatementDispatcher.replicateTransaction(request);
    }

}
