package virgilistrate.S7L5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;


    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private long capacity;

    @Column(name = "occupied_seats", nullable = false)
    private long occupiedSeats = 0;


    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User creator;


    @OneToMany(mappedBy = "event")
    private List<Booking> bookings;

    public Event(String title, String description, LocalDateTime date, String location, long capacity, User creator) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.creator = creator;
        this.occupiedSeats = 0;
    }
}