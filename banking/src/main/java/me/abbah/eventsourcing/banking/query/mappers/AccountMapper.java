package me.abbah.eventsourcing.banking.query.mappers;

import me.abbah.eventsourcing.banking.query.dto.AccountDTO;
import me.abbah.eventsourcing.banking.query.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO fromAccount(Account account);
    Account toAccount(AccountDTO dto);
}
