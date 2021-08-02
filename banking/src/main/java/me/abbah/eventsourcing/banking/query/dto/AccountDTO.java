package me.abbah.eventsourcing.banking.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String id;

    private BigInteger balance;

    private String currency;

    private AccountStatus status;
}
