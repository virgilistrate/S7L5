package virgilistrate.S7L5.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import virgilistrate.S7L5.entities.User;
import virgilistrate.S7L5.exceptions.ValidationException;
import virgilistrate.S7L5.payloads.LoginDTO;
import virgilistrate.S7L5.payloads.RegisterDTO;
import virgilistrate.S7L5.payloads.TokenDTO;
import virgilistrate.S7L5.services.AuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        return authService.register(body);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        String token = authService.login(body);
        return new TokenDTO(token);
    }
}