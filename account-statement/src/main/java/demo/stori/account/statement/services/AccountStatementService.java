package demo.stori.account.statement.services;

import demo.stori.account.statement.repository.AccountStatementRepository;
import demo.stori.account.statement.representation.AccountStatementReady;
import demo.stori.account.statement.representation.TransactionRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;

import static demo.stori.account.statement.util.TransactionUtil.extractDateAndResetDay;
import static demo.stori.account.statement.util.TransactionUtil.stringTransactionToDecimal;

@Service
public class AccountStatementService {

    private final AccountStatementRepository accountStatementRepository;

    public AccountStatementService(AccountStatementRepository accountStatementRepository) {
        this.accountStatementRepository = accountStatementRepository;
    }

    public void create(TransactionRequest request) throws ParseException {
        BigDecimal amount = stringTransactionToDecimal(request.getTransaction());
        BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

        if (amount.compareTo(BigDecimal.ONE) < 0) {
            debit = amount;
        } else {
            credit = amount;
        }

        AccountStatementReady statementReady = AccountStatementReady.builder()
                .accountId(request.getAccount())
                .date(extractDateAndResetDay(request.getDate()))
                .credit(credit)
                .debit(debit)
                .build();

        accountStatementRepository.recordStatement(statementReady);
    }

}
