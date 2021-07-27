package me.abbah.eventsourcing.banking.coreapi.events;

import lombok.Getter;

public abstract class BaseEvent<T> {
    @Getter private final T id;

    protected BaseEvent(T id) {
        this.id = id;
    }
}
