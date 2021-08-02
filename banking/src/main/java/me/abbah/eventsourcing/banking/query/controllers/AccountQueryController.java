package me.abbah.eventsourcing.banking.query.controllers;

import lombok.AllArgsConstructor;
import me.abbah.eventsourcing.banking.query.dto.*;
import me.abbah.eventsourcing.banking.query.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @GetMapping(path = "/{accountId}/watch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountDTO> subscribeToAccount(@PathVariable String accountId) {
        SubscriptionQueryResult<AccountDTO, AccountDTO> res = gateway.subscriptionQuery(
                new GetAccountByIdQuery(accountId),
                ResponseTypes.instanceOf(AccountDTO.class),
                ResponseTypes.instanceOf(AccountDTO.class)
        );

        return res.initialResult().concatWith(res.updates());
    }
}
