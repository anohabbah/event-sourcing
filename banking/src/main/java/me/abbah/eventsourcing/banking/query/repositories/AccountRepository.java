package me.abbah.eventsourcing.banking.query.repositories;

import me.abbah.eventsourcing.banking.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
