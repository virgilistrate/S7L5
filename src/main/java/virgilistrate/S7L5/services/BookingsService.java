package virgilistrate.S7L5.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import virgilistrate.S7L5.entities.Booking;
import virgilistrate.S7L5.entities.Event;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.BadRequestException;
import virgilistrate.S7L5.exceptions.ForbiddenException;
import virgilistrate.S7L5.exceptions.NotFoundException;
import virgilistrate.S7L5.repositories.BookingRepository;
import virgilistrate.S7L5.repositories.EventRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BookingsService {

    private final BookingRepository bookingsRepo;
    private final EventRepository eventsRepo;

    public BookingsService(BookingRepository bookingsRepo, EventRepository eventsRepo) {
        this.bookingsRepo = bookingsRepo;
        this.eventsRepo = eventsRepo;
    }

    private void onlyUser(User u) {
        if (!u.getRole().name().equals("USER"))
            throw new ForbiddenException("Solo gli USER possono prenotare");
    }

    public List<Booking> findMyBookings(User user) {
        onlyUser(user);
        return bookingsRepo.findByUserId(user.getId());
    }

    @Transactional
    public Booking create(UUID eventId, long seats, User user) {
        onlyUser(user);

        Event event = eventsRepo.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));

        if (bookingsRepo.existsByUserIdAndEventId(user.getId(), eventId))
            throw new BadRequestException("Hai già una prenotazione per questo evento");

        long available = event.getCapacity() - event.getOccupiedSeats();
        if (seats > available)
            throw new BadRequestException("Posti insufficienti. Disponibili: " + available);

        event.setOccupiedSeats(event.getOccupiedSeats() + seats);

        Booking booking = new Booking(seats, user, event);

        eventsRepo.save(event);
        return bookingsRepo.save(booking);
    }

    @Transactional
    public void delete(UUID bookingId, User user) {
        onlyUser(user);

        Booking booking = bookingsRepo.findByIdAndUserId(bookingId, user.getId())
                .orElseThrow(() -> new NotFoundException(bookingId));

        Event event = booking.getEvent();

        long newOccupied = event.getOccupiedSeats() - booking.getSeats();
        if (newOccupied < 0) newOccupied = 0;

        event.setOccupiedSeats(newOccupied);

        bookingsRepo.delete(booking);
        eventsRepo.save(event);
    }
}