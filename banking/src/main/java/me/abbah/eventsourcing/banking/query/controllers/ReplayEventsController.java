package me.abbah.eventsourcing.banking.query.controllers;

import lombok.AllArgsConstructor;
import me.abbah.eventsourcing.banking.query.services.ReplayEventsHandlerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/query/accounts")
@AllArgsConstructor
public class ReplayEventsController {
    private ReplayEventsHandlerService eventsPlayer;

    public String replayEvents() {
        eventsPlayer.replay();
        return "Events replayed successfully !";
    }
}
