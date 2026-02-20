package virgilistrate.S7L5.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import virgilistrate.S7L5.entities.Event;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.ForbiddenException;
import virgilistrate.S7L5.exceptions.ValidationException;
import virgilistrate.S7L5.payloads.EventDTO;
import virgilistrate.S7L5.services.EventsService;
import virgilistrate.S7L5.services.UsersService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizer/events")
public class OrganizerEventsController {

    private final EventsService eventsService;
    private final UsersService usersService;

    public OrganizerEventsController(EventsService eventsService, UsersService usersService) {
        this.eventsService = eventsService;
        this.usersService = usersService;
    }

    private void onlyOrganizer(User u) {
        if (!u.getRole().name().equals("ORGANIZER"))
            throw new ForbiddenException("Solo gli ORGANIZER possono eseguire questa operazione");
    }

    @PostMapping
    public Event create(@RequestBody @Valid EventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        User current = usersService.getCurrentUser();
        onlyOrganizer(current);
        return eventsService.create(body, current);
    }

    @GetMapping("/me")
    public List<Event> mine() {
        User current = usersService.getCurrentUser();
        onlyOrganizer(current);
        return eventsService.findMine(current);
    }

    @PutMapping("/{eventId}")
    public Event update(@PathVariable UUID eventId, @RequestBody @Valid EventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        User current = usersService.getCurrentUser();
        onlyOrganizer(current);
        return eventsService.update(eventId, body, current);
    }

    @DeleteMapping("/{eventId}")
    public void delete(@PathVariable UUID eventId) {
        User current = usersService.getCurrentUser();
        onlyOrganizer(current);
        eventsService.delete(eventId, current);
    }
}