package me.abbah.eventsourcing.banking.query.queries;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountHistoryQuery {
    private String accountId;
}
