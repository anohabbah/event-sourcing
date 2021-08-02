package me.abbah.eventsourcing.banking.query.queries;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountByIdQuery {
    private String accountId;
}
