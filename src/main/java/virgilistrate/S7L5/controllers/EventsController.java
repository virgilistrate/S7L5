package virgilistrate.S7L5.controllers;

import org.springframework.web.bind.annotation.*;
import virgilistrate.S7L5.entities.Event;
import virgilistrate.S7L5.services.EventsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public List<Event> findAll() {
        return eventsService.findAll();
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId) {
        return eventsService.findById(eventId);
    }
}