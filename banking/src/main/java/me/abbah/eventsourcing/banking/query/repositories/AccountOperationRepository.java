package me.abbah.eventsourcing.banking.query.repositories;

import me.abbah.eventsourcing.banking.query.entities.Account;
import me.abbah.eventsourcing.banking.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String accountId);
}
