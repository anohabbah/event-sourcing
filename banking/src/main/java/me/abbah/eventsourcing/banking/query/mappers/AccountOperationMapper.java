package me.abbah.eventsourcing.banking.query.mappers;

import me.abbah.eventsourcing.banking.query.dto.AccountOperationDTO;
import me.abbah.eventsourcing.banking.query.entities.AccountOperation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountOperationMapper {
    AccountOperationDTO fromOperation(AccountOperation operation);
    AccountOperation toOperation(AccountOperationDTO dto);
}
