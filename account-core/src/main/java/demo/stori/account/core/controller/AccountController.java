package demo.stori.account.core.controller;

import demo.stori.account.core.representation.request.BalanceRequest;
import demo.stori.account.core.service.AccountService;
import org.springframework.http.HttpStatus;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/{account_id}/balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBalance(@PathVariable("account_id") Long accountId, @RequestBody BalanceRequest request) {
        accountService.updateBalance(accountId, request);
    }

}
