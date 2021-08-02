package me.abbah.eventsourcing.banking.query.dto;

import lombok.*;
import me.abbah.eventsourcing.banking.query.entities.OperationType;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationDTO {
    private Long id;
    private Date date;
    private BigInteger amount;
    private OperationType type;
}
