package pro.sorokovsky.sorokchatserverspring.exception.base;

public class InvalidErrorException extends RuntimeException {
    public InvalidErrorException(String message) {
        super(message);
    }
}
