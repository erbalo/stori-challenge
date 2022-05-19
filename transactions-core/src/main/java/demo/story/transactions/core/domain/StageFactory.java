package demo.story.transactions.core.domain;

import demo.story.transactions.core.domain.chain.PendingTransactionStage;
import demo.story.transactions.core.domain.chain.Stage;
import demo.story.transactions.core.domain.chain.UpdateBalanceStage;
import demo.story.transactions.core.domain.chain.UpdateTransactionStage;
import demo.story.transactions.core.domain.chain.ValidateTransactionStage;
import org.springframework.stereotype.Component;

@Component
public class StageFactory {

    private final UpdateBalanceStage updateBalanceStage;
    private final PendingTransactionStage pendingTransactionStage;
    private final UpdateTransactionStage updateTransactionStage;
    private final ValidateTransactionStage validateTransactionStage;

    public StageFactory(UpdateBalanceStage updateBalanceStage, PendingTransactionStage recordTransactionStage,
                        UpdateTransactionStage updateTransactionStage, ValidateTransactionStage validateTransactionStage) {
        this.updateBalanceStage = updateBalanceStage;
        this.pendingTransactionStage = recordTransactionStage;
        this.updateTransactionStage = updateTransactionStage;
        this.validateTransactionStage = validateTransactionStage;
    }

    public Stage transactionPipeline() {
        Stage stage = validateTransactionStage;
        stage.linkWith(pendingTransactionStage)
                .linkWith(updateBalanceStage)
                .linkWith(updateTransactionStage);

        return stage;
    }

}
