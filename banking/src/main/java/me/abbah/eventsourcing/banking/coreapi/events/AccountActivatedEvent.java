package me.abbah.eventsourcing.banking.coreapi.events;

import lombok.Getter;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter private final AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
