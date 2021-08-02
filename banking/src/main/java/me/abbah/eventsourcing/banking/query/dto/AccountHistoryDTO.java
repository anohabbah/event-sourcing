package me.abbah.eventsourcing.banking.query.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistoryDTO {
    private AccountDTO account;
    private List<AccountOperationDTO> operations;
}
