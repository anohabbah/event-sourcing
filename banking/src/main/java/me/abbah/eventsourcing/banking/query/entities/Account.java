package me.abbah.eventsourcing.banking.query.entities;

import lombok.*;
import me.abbah.eventsourcing.banking.coreapi.enums.AccountStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "accounts")
public class Account {
    @Id private String id;
    private BigInteger balance;
    private String currency;
    @Enumerated(EnumType.STRING) private AccountStatus status;
}
