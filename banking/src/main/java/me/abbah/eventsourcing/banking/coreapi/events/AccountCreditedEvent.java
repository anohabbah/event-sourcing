package me.abbah.eventsourcing.banking.coreapi.events;

import lombok.Getter;

import java.math.BigInteger;

public class AccountCreditedEvent extends BaseEvent<String> {
    @Getter private final BigInteger amount;
    @Getter private final String currency;

    public AccountCreditedEvent(String id, BigInteger amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
