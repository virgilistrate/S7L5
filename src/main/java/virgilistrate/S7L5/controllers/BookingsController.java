package virgilistrate.S7L5.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import virgilistrate.S7L5.entities.Booking;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.ValidationException;
import virgilistrate.S7L5.payloads.BookingDTO;
import virgilistrate.S7L5.services.BookingsService;
import virgilistrate.S7L5.services.UsersService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingsController {

    private final BookingsService bookingsService;
    private final UsersService usersService;

    public BookingsController(BookingsService bookingsService, UsersService usersService) {
        this.bookingsService = bookingsService;
        this.usersService = usersService;
    }

    @GetMapping("/me")
    public List<Booking> myBookings() {
        User current = usersService.getCurrentUser();
        return bookingsService.findMyBookings(current);
    }

    @PostMapping("/{eventId}")
    public Booking book(@PathVariable UUID eventId, @RequestBody @Valid BookingDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        User current = usersService.getCurrentUser();
        return bookingsService.create(eventId, body.seats(), current);
    }

    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable UUID bookingId) {
        User current = usersService.getCurrentUser();
        bookingsService.delete(bookingId, current);
    }
}