package demo.stori.account.statement.domain.mapper;

import demo.stori.account.statement.entity.AccountStatement;
import demo.stori.account.statement.representation.AccountStatementRepresentation;
import org.springframework.stereotype.Component;

import static demo.stori.account.statement.util.TransactionUtil.getMonthFromDate;

@Component
public class AccountStatementMapper implements Mapper<AccountStatementRepresentation, AccountStatement> {

    @Override
    public AccountStatement fromDto(AccountStatementRepresentation dto) {
        return null;
    }

    @Override
    public AccountStatementRepresentation fromEntity(AccountStatement entity) {
        String month = getMonthFromDate(entity.getDate()).orElse("not-valid-month");

        return AccountStatementRepresentation.builder()
                .accountId(entity.getAccountId())
                .credit(entity.getCredit())
                .debit(entity.getDebit())
                .transactions(entity.getTransactions())
                .month(month)
                .build();
    }
}
