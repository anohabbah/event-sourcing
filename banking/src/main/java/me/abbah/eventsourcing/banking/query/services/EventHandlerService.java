package me.abbah.eventsourcing.banking.query.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;
import me.abbah.eventsourcing.banking.coreapi.events.AccountActivatedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountCreatedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountCreditedEvent;
import me.abbah.eventsourcing.banking.coreapi.events.AccountDebitedEvent;
import me.abbah.eventsourcing.banking.query.entities.Account;
import me.abbah.eventsourcing.banking.query.entities.AccountOperation;
import me.abbah.eventsourcing.banking.query.entities.OperationType;
import me.abbah.eventsourcing.banking.query.repositories.AccountOperationRepository;
import me.abbah.eventsourcing.banking.query.repositories.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class EventHandlerService {
    private AccountRepository accountRepository;
    private AccountOperationRepository operationRepository;

    @ResetHandler
    public void resetHandler() {
        log.info("Resetting DB...");
        accountRepository.deleteAll();
        operationRepository.deleteAll();
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("[QUERY] : AccountCreatedEvent received...");
        final Account account = new Account(
                event.getId(), event.getInitialBalance(),
                event.getCurrency(), AccountStatus.CREATED);

        accountRepository.save(account);
    }

    @EventHandler
    @Transactional
    public void on(AccountActivatedEvent event) {
        log.info("[QUERY] : AccountCreatedEvent received...");
        final Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());

        accountRepository.save(account);
    }

    @Transactional
    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("[QUERY] : AccountCreatedEvent received...");

        final Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance().add(event.getAmount()));

        final AccountOperation operation = new AccountOperation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);

        operationRepository.save(operation);
        accountRepository.save(account);
    }

    @Transactional
    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("[QUERY] : AccountDebitedEvent received...");

        final Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance().subtract(event.getAmount()));

        final AccountOperation operation = new AccountOperation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);

        operationRepository.save(operation);
        accountRepository.save(account);
    }
}