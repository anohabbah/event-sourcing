package me.abbah.eventsourcing.banking.coreapi.commands;

import lombok.Getter;

import java.math.BigInteger;

public class DebitAccountCommand extends BaseCommand<String> {
    @Getter private final BigInteger amount;
    @Getter private final String currency;

    public DebitAccountCommand(String id, BigInteger amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
