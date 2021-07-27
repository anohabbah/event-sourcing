package me.abbah.eventsourcing.banking.commands.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountRequestDTO {
    private String accountId;
    private BigInteger amount;
    private String currency;
}
