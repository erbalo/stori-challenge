package demo.stori.account.core.domain.mapper;

import demo.stori.account.core.entity.Account;
import demo.stori.account.core.representation.AccountRepresentation;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<AccountRepresentation, Account> {

    @Override
    public Account fromDto(AccountRepresentation dto) {
        return Account.builder()
                .amount(dto.getBalance())
                .id(dto.getId())
                .build();
    }

    @Override
    public AccountRepresentation fromEntity(Account entity) {
        return AccountRepresentation.builder()
                .balance(entity.getAmount())
                .id(entity.getId())
                .build();
    }
}
