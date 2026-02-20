package virgilistrate.S7L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import virgilistrate.S7L5.entities.Role;

public record RegisterDTO(
        @NotBlank(message = "Username obbligatorio") String username,
        @NotBlank(message = "Email obbligatoria") @Email(message = "Email non valida") String email,
        @NotBlank(message = "Password obbligatoria") @Size(min = 8, message = "Password troppo corta (min 8 aratteri)") String password,
        Role role
) {}