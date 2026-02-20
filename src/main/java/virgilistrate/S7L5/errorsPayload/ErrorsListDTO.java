package virgilistrate.S7L5.errorsPayload;

import java.util.List;

public record ErrorsListDTO(String message, List<String> errors) {
}