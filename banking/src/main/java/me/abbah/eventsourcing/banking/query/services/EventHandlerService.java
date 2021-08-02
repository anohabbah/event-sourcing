package me.abbah.eventsourcing.banking.query.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;
import me.abbah.eventsourcing.banking.coreapi.events.*;
import me.abbah.eventsourcing.banking.query.entities.*;
import me.abbah.eventsourcing.banking.query.mappers.AccountMapper;
import me.abbah.eventsourcing.banking.query.queries.GetAccountByIdQuery;
import me.abbah.eventsourcing.banking.query.repositories.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class EventHandlerService {
    private AccountRepository accountRepository;
    private AccountOperationRepository operationRepository;
    private QueryUpdateEmitter emitter;
    private AccountMapper accountMapper;

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
        Account savedAccount = accountRepository.save(account);

        emitter.emit(
                GetAccountByIdQuery.class,
                query -> query.getAccountId().equals(event.getId()),
                accountMapper.fromAccount(savedAccount)
        );
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
        Account savedAccount = accountRepository.save(account);

        emitter.emit(
                msg -> ((GetAccountByIdQuery) msg.getPayload()).getAccountId().equals(event.getId()),
                accountMapper.fromAccount(savedAccount)
        );
    }
}
