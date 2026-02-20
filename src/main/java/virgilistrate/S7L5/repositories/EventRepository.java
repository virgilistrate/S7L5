package virgilistrate.S7L5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import virgilistrate.S7L5.entities.Event;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByCreatorId(UUID creatorId);
}