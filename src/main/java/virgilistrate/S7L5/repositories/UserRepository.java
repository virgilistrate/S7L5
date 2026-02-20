package virgilistrate.S7L5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import virgilistrate.S7L5.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}