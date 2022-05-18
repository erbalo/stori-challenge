package demo.stori.account.core.service;

import demo.stori.account.core.repository.AccountRepository;
import demo.stori.account.core.representation.request.BalanceRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void updateBalance(Long accountId, BalanceRequest request){
        accountRepository.updateBalance(accountId, request);
    }
}
