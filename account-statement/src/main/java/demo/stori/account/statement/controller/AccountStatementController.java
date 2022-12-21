package demo.stori.account.statement.controller;

import demo.stori.account.statement.domain.mapper.AccountStatementMapper;
import demo.stori.account.statement.entity.AccountStatement;
import demo.stori.account.statement.representation.AccountStatementRepresentation;
import demo.stori.account.statement.services.AccountStatementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account-statements")
public class AccountStatementController {

    private final AccountStatementService accountStatementService;
    private final AccountStatementMapper statementMapper;

    public AccountStatementController(AccountStatementService accountStatementService, AccountStatementMapper statementMapper) {
        this.accountStatementService = accountStatementService;
        this.statementMapper = statementMapper;
    }

    @GetMapping("/{account_id}")
    public List<AccountStatementRepresentation> statementsByYear(@PathVariable("account_id") Long accountId, @RequestParam int year) {
        List<AccountStatement> statements = accountStatementService.statementsBy(accountId, year);
        return statements.stream()
                .map(statementMapper::fromEntity)
                .collect(Collectors.toList());
    }

}
