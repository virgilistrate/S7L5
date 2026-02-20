package virgilistrate.S7L5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import virgilistrate.S7L5.entities.Role;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.BadRequestException;
import virgilistrate.S7L5.exceptions.UnautorizedException;
import virgilistrate.S7L5.payloads.LoginDTO;
import virgilistrate.S7L5.payloads.RegisterDTO;
import virgilistrate.S7L5.repositories.UserRepository;
import virgilistrate.S7L5.security.JWTTools;

@Service
public class AuthService {

    private final UserRepository usersRepo;
    private final PasswordEncoder bcrypt;
    private final JWTTools jwtTools;

    public AuthService(UserRepository usersRepo, PasswordEncoder bcrypt, JWTTools jwtTools) {
        this.usersRepo = usersRepo;
        this.bcrypt = bcrypt;
        this.jwtTools = jwtTools;
    }

    public User register(RegisterDTO body) {
        if (usersRepo.existsByEmail(body.email())) throw new BadRequestException("Email già in uso");

        Role roleToSet = (body.role() == null) ? Role.USER : body.role();

        User newUser = new User(body.username(), body.email(), bcrypt.encode(body.password()), roleToSet);
        return usersRepo.save(newUser);
    }

    public String login(LoginDTO body) {
        User found = usersRepo.findByEmail(body.email())
                .orElseThrow(() -> new UnautorizedException("Credenziali non valide"));

        if (!bcrypt.matches(body.password(), found.getPassword()))
            throw new UnautorizedException("Credenziali non valide");

        return jwtTools.generateToken(found);
    }
}