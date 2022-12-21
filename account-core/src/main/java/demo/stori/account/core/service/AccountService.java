package demo.stori.account.core.service;

import demo.stori.account.core.entity.Account;
import demo.stori.account.core.repository.AccountRepository;
import demo.stori.account.core.representation.BalanceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getBalance(Long accountId) {
        return accountRepository.getBy(accountId);
    }

    public void updateBalance(Long accountId, BalanceRequest request) {
        log.info("Requesting update balance for account {}", accountId);
        accountRepository.updateBalance(accountId, request);
    }
}
