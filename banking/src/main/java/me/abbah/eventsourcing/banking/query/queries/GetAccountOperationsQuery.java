package me.abbah.eventsourcing.banking.query.queries;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountOperationsQuery {
    private String accountId;
}
