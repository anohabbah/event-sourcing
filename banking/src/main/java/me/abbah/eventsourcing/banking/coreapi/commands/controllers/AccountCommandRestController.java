package me.abbah.eventsourcing.banking.coreapi.commands.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.abbah.eventsourcing.banking.commands.dto.CreateAccountRequestDTO;
import me.abbah.eventsourcing.banking.commands.dto.CreditAccountRequestDTO;
import me.abbah.eventsourcing.banking.commands.dto.DebitAccountRequestDTO;
import me.abbah.eventsourcing.banking.coreapi.commands.CreateAccountCommand;
import me.abbah.eventsourcing.banking.coreapi.commands.CreditAccountCommand;
import me.abbah.eventsourcing.banking.coreapi.commands.DebitAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "api/commands/accounts")
public class AccountCommandRestController {
    private final CommandGateway gateway;
    private final EventStore store;

    @PostMapping(path = "")
    public CompletableFuture<Object> create(@RequestBody CreateAccountRequestDTO dto) {
        log.info(dto.getInitialBalance().toString());
        return gateway.send(
                new CreateAccountCommand(UUID.randomUUID().toString(), dto.getInitialBalance(), dto.getCurrency())
        );
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<Object> credit(@RequestBody CreditAccountRequestDTO dto) {
        log.info("Account credited to " + dto.getAmount().toString() + " " + dto.getCurrency());
        return gateway.send(
                new CreditAccountCommand(dto.getAccountId(), dto.getAmount(), dto.getCurrency())
        );
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<Object> debit(@RequestBody DebitAccountRequestDTO dto) {
        log.info("Account debited to " + dto.getAmount().toString() + " " + dto.getCurrency());
        return gateway.send(
                new DebitAccountCommand(dto.getAccountId(), dto.getAmount(), dto.getCurrency())
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @GetMapping(path = "/{accountId}/events")
    public Stream accountEvents(@PathVariable String accountId) {
        return store.readEvents(accountId).asStream();
    }
}
