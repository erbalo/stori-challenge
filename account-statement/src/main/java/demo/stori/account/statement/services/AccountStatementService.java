package demo.stori.account.statement.services;

import demo.stori.account.statement.entity.AccountStatement;
import demo.stori.account.statement.repository.AccountStatementRepository;
import demo.stori.account.statement.representation.AccountStatementReady;
import demo.stori.account.statement.representation.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static demo.stori.account.statement.util.TransactionUtil.extractDateAndResetDay;
import static demo.stori.account.statement.util.TransactionUtil.stringTransactionToDecimal;

@Slf4j
@Service
public class AccountStatementService {

    private final AccountStatementRepository accountStatementRepository;

    public AccountStatementService(AccountStatementRepository accountStatementRepository) {
        this.accountStatementRepository = accountStatementRepository;
    }

    public void create(TransactionRequest request) {
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

    public List<AccountStatement> statementsBy(Long accountId, int year) {
        log.info("Requesting statements for account [{}] with year [{}]", accountId, year);
        return accountStatementRepository.getStatementBy(accountId, year).stream()
                .sorted(Comparator.comparing(AccountStatement::getDate))
                .collect(Collectors.toList());
    }

}
