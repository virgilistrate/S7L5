package virgilistrate.S7L5.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorMessages = new ArrayList<>();

    public ValidationException(List<String> errorMessages) {
        super("Errore nel payload.");
        this.errorMessages = errorMessages;
    }
}