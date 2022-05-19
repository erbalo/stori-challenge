package demo.stori.account.core.service;

import demo.stori.account.core.repository.AccountRepository;
import demo.stori.account.core.representation.request.BalanceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void updateBalance(Long accountId, BalanceRequest request) {
        log.info("Requesting update balance for account {}", accountId);
        accountRepository.updateBalance(accountId, request);
    }
}
