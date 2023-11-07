package movieticket.exceptions;

import java.io.Serial;

public class IntegrityViolationException extends RuntimeException{

	@Serial
    private static final long serialVersionUID = 1L;

	public IntegrityViolationException(String msg){
		super(msg);
	}
}
