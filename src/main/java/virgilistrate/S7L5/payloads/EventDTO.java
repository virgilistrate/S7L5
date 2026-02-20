package virgilistrate.S7L5.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EventDTO(
        @NotBlank(message = "Titolo obbligatorio") String title,
        @NotBlank(message = "Descrizione obbligatoria") String description,
        @NotNull(message = "Data obbligatoria") @Future(message = "La data deve essere futura") LocalDateTime date,
        @NotBlank(message = "Luogo obbligatorio") String location,
        @Positive(message = "La capienza deve essere > 0") long capacity
) {}