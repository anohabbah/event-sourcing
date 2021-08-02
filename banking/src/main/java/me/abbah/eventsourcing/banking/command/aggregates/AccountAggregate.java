package me.abbah.eventsourcing.banking.command.aggregates;

import lombok.extern.slf4j.Slf4j;
import me.abbah.eventsourcing.banking.command.exceptions.BalanceNotSufficientException;
import me.abbah.eventsourcing.banking.coreapi.commands.CreateAccountCommand;
import me.abbah.eventsourcing.banking.coreapi.commands.CreditAccountCommand;
import me.abbah.eventsourcing.banking.coreapi.commands.DebitAccountCommand;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;
import me.abbah.eventsourcing.banking.coreapi.events.AccountActivatedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountCreatedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountCreditedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigInteger;

@Slf4j
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private BigInteger balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(final CreateAccountCommand cmd) {
        log.info("CreateAccountCommand received...");

        /* Business logic here... */

        AggregateLifecycle.apply(new AccountCreatedEvent(cmd.getId(), cmd.getInitialBalance(), cmd.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("AccountCreatedEvent occurred");
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;

        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("AccountActivatedEvent occurred");
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(final CreditAccountCommand cmd) {
        log.info("CreditAccountCommand received...");

        /* Business logic here... */

        AggregateLifecycle.apply(new AccountCreditedEvent(cmd.getId(), cmd.getAmount(), cmd.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("AccountCreditedEvent occurred...");
        this.balance = this.balance.add(event.getAmount());

//        this.currency = event.getCurrency();
//        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED));
    }

    @CommandHandler
    public void handle(final DebitAccountCommand cmd) {
        log.info("DebitAccountCommand received...");


        if (this.balance.subtract(cmd.getAmount()).intValue() < 0) {
            throw new BalanceNotSufficientException("Balance not sufficient exception.");
        }

        AggregateLifecycle.apply(new AccountDebitedEvent(cmd.getId(), cmd.getAmount(), cmd.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) throws BalanceNotSufficientException {
        log.info("AccountDebitedEvent occurred...");
        this.balance = this.balance.subtract(event.getAmount());

//        this.currency = event.getCurrency();
//        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED));
    }
}
