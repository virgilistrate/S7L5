package virgilistrate.S7L5.payloads;

import jakarta.validation.constraints.Positive;

public record BookingDTO(
        @Positive(message = "I posti devono essere > 0")
        long seats
) {}