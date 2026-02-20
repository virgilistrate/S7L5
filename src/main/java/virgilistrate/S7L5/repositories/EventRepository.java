package virgilistrate.S7L5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import virgilistrate.S7L5.entities.Event;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}