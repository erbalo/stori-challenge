package demo.stori.account.core.controller;

import demo.stori.account.core.domain.mapper.AccountMapper;
import demo.stori.account.core.exception.ResourceNotFoundException;
import demo.stori.account.core.representation.AccountRepresentation;
import demo.stori.account.core.representation.BalanceRequest;
import demo.stori.account.core.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/{account_id}/balance")
    public AccountRepresentation getBalance(@PathVariable("account_id") Long accountId) {
        return accountService.getBalance(accountId)
                .map(accountMapper::fromEntity)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("/{account_id}/balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBalance(@PathVariable("account_id") Long accountId, @RequestBody BalanceRequest request) {
        accountService.updateBalance(accountId, request);
    }

}
