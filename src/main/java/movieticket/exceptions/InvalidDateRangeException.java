package movieticket.exceptions;

import java.io.Serial;

public class InvalidDateRangeException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
