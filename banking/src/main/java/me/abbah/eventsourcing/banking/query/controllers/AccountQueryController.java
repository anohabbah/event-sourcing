package me.abbah.eventsourcing.banking.query.controllers;

import lombok.AllArgsConstructor;
import me.abbah.eventsourcing.banking.query.dto.*;
import me.abbah.eventsourcing.banking.query.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/api/query/accounts")
public class AccountQueryController {
    private QueryGateway gateway;

    @GetMapping("/{accountId}")
    public CompletableFuture<AccountDTO> getAccounts(@PathVariable String accountId) {
        return gateway.query(new GetAccountByIdQuery(accountId), AccountDTO.class);
    }

    @GetMapping("/{accountId}/operations")
    public CompletableFuture<List<AccountOperationDTO>> getOperations(@PathVariable String accountId) {
        return gateway.query(
                new GetAccountOperationsQuery(accountId),
                ResponseTypes.multipleInstancesOf(AccountOperationDTO.class)
        );
    }

    @GetMapping("/{accountId}/history")
    public CompletableFuture<AccountHistoryDTO> getHistory(@PathVariable String accountId) {
        return gateway.query(
                new GetAccountHistoryQuery(accountId),
                ResponseTypes.instanceOf(AccountHistoryDTO.class)
        );
    }
}
