package pro.sorokovsky.sorokchatserverspring.exception.base;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends HttpException {
    private static final String WORD = "already";

    public AlreadyExistsException(String entity, String key, String value, HttpStatus statusCode) {
        super(entity, key, value, WORD, statusCode);
    }

    public AlreadyExistsException(String entity, String key, String value, HttpStatus statusCode, Throwable cause) {
        super(entity, key, value, WORD, statusCode, cause);
    }
}
