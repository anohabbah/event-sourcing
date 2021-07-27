package me.abbah.eventsourcing.banking.coreapi.commands;

import lombok.Getter;

import java.math.BigInteger;

public class CreditAccountCommand extends BaseCommand<String> {
    @Getter private final BigInteger amount;
    @Getter private final String currency;

    public CreditAccountCommand(String id, BigInteger amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
