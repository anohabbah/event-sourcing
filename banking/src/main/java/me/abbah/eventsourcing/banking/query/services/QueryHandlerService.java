package me.abbah.eventsourcing.banking.query.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.abbah.eventsourcing.banking.query.dto.AccountDTO;
import me.abbah.eventsourcing.banking.query.dto.AccountHistoryDTO;
import me.abbah.eventsourcing.banking.query.dto.AccountOperationDTO;
import me.abbah.eventsourcing.banking.query.entities.Account;
import me.abbah.eventsourcing.banking.query.entities.AccountOperation;
import me.abbah.eventsourcing.banking.query.mappers.AccountMapper;
import me.abbah.eventsourcing.banking.query.mappers.AccountOperationMapper;
import me.abbah.eventsourcing.banking.query.queries.GetAccountByIdQuery;
import me.abbah.eventsourcing.banking.query.queries.GetAccountHistoryQuery;
import me.abbah.eventsourcing.banking.query.queries.GetAccountOperationsQuery;
import me.abbah.eventsourcing.banking.query.repositories.*;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class QueryHandlerService {
    private AccountRepository accountRepository;
    private AccountOperationRepository operationRepository;
    private AccountMapper accountMapper;
    private AccountOperationMapper operationMapper;

    @QueryHandler
    public AccountDTO handle(GetAccountByIdQuery query) {
        final Account account = accountRepository.findById(query.getAccountId()).get();

        return accountMapper.fromAccount(account);
    }

    @QueryHandler
    public List<AccountOperationDTO> handle(GetAccountOperationsQuery query) {
        final List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());

        return operations.stream()
                .map(op -> operationMapper.fromOperation(op))
                .collect(Collectors.toList());
    }

    @QueryHandler
    public AccountHistoryDTO handle(GetAccountHistoryQuery query) {
        final Account account = accountRepository.findById(query.getAccountId()).get();

        final List<AccountOperationDTO> operations = operationRepository.findByAccountId(query.getAccountId())
                .stream()
                .map(op -> operationMapper.fromOperation(op))
                .collect(Collectors.toList());

        return new AccountHistoryDTO(accountMapper.fromAccount(account), operations);
    }
}
