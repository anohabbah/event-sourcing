package me.abbah.eventsourcing.banking.coreapi.events;

import lombok.Getter;

import java.math.BigInteger;

public class AccountCreatedEvent extends BaseEvent<String> {
    @Getter private final BigInteger initialBalance;
    @Getter private final String currency;

    public AccountCreatedEvent(String id, BigInteger initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
