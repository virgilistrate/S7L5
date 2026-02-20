package virgilistrate.S7L5.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.UnautorizedException;

@Service
public class UsersService {

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User u) return u;
        throw new UnautorizedException("Utente non autenticato");
    }
}