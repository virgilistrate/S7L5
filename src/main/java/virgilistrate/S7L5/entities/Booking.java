package virgilistrate.S7L5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "booked_date", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime bookedDate;

    @Column(nullable = false)
    private long seats;


    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Event event;

    public Booking(long seats, User user, Event event) {
        this.seats = seats;
        this.user = user;
        this.event = event;
    }

    @PrePersist
    public void onCreate() {
        this.bookedDate = LocalDateTime.now();
    }
}