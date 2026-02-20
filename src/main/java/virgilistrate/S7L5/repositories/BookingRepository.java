package virgilistrate.S7L5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import virgilistrate.S7L5.entities.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    boolean existsByUserIdAndEventId(UUID userId, UUID eventId);

    List<Booking> findByUserId(UUID userId);

    Optional<Booking> findByIdAndUserId(UUID bookingId, UUID userId);
}