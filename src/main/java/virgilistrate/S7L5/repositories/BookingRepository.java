package virgilistrate.S7L5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import virgilistrate.S7L5.entities.Booking;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}