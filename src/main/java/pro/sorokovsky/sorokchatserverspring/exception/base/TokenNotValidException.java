package pro.sorokovsky.sorokchatserverspring.exception.base;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException(String message) {
        super(message);
    }
}
