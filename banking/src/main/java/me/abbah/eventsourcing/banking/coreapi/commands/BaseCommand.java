package me.abbah.eventsourcing.banking.coreapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    private final T id;

    protected BaseCommand(T id) {
        this.id = id;
    }
}
