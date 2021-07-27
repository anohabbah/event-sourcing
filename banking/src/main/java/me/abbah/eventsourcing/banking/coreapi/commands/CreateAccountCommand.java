package me.abbah.eventsourcing.banking.coreapi.commands;

import lombok.Getter;

import java.math.BigInteger;

public class CreateAccountCommand extends BaseCommand<String> {
    @Getter private final BigInteger initialBalance;
    @Getter private final String currency;

    public CreateAccountCommand(String id, BigInteger initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
