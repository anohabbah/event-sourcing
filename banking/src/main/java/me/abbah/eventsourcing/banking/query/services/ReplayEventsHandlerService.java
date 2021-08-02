package me.abbah.eventsourcing.banking.query.services;

import lombok.AllArgsConstructor;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReplayEventsHandlerService {
    private EventProcessingConfiguration config;

    public void replay() {
        String name = "me.abbah.eventsourcing.banking.query.services";

        config.eventProcessor(name, TrackingEventProcessor.class)
                .ifPresent(processor -> {
                    processor.shutDown();
                    processor.resetTokens();
                    processor.start();
                });
    }
}
