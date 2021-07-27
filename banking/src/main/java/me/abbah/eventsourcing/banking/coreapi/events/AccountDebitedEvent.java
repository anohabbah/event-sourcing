package me.abbah.eventsourcing.banking.coreapi.events;

import lombok.Getter;

import java.math.BigInteger;

public class AccountDebitedEvent extends BaseEvent<String> {
    @Getter private final BigInteger amount;
    @Getter private final String currency;

    public AccountDebitedEvent(String id, BigInteger amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
