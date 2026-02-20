package virgilistrate.S7L5.services;

import org.springframework.stereotype.Service;
import virgilistrate.S7L5.entities.Event;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.BadRequestException;
import virgilistrate.S7L5.exceptions.ForbiddenException;
import virgilistrate.S7L5.exceptions.NotFoundException;
import virgilistrate.S7L5.payloads.EventDTO;
import virgilistrate.S7L5.repositories.EventRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EventsService {

    private final EventRepository eventsRepo;

    public EventsService(EventRepository eventsRepo) {
        this.eventsRepo = eventsRepo;
    }

    public List<Event> findAll() {
        return eventsRepo.findAll();
    }

    public Event findById(UUID id) {
        return eventsRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Event> findMine(User creator) {
        return eventsRepo.findByCreatorId(creator.getId());
    }

    public Event create(EventDTO body, User creator) {
        Event e = new Event(body.title(), body.description(), body.date(), body.location(), body.capacity(), creator);
        return eventsRepo.save(e);
    }

    public Event update(UUID id, EventDTO body, User current) {
        Event found = findById(id);

        if (!found.getCreator().getId().equals(current.getId()))
            throw new ForbiddenException("Non puoi modificare un evento che non hai creato");

        if (body.capacity() < found.getOccupiedSeats())
            throw new BadRequestException("La nuova capienza non può essere inferiore ai posti già occupati");

        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setDate(body.date());
        found.setLocation(body.location());
        found.setCapacity(body.capacity());

        return eventsRepo.save(found);
    }

    public void delete(UUID id, User current) {
        Event found = findById(id);

        if (!found.getCreator().getId().equals(current.getId()))
            throw new ForbiddenException("Non puoi eliminare un evento che non hai creato");

        eventsRepo.delete(found);
    }
}