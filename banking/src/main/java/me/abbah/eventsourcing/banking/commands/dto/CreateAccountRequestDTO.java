package me.abbah.eventsourcing.banking.commands.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequestDTO {
    private BigInteger initialBalance;
    private String currency;
}
