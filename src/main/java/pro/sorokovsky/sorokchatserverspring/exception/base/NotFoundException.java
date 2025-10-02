package pro.sorokovsky.sorokchatserverspring.exception.base;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {
    private static final String WORD = "not";

    public NotFoundException(String entity, String key, String value) {
        super(entity, key, value, WORD, HttpStatus.BAD_REQUEST);
    }

    public NotFoundException(String entity, String key, String value, Throwable cause) {
        super(entity, key, value, WORD, HttpStatus.BAD_REQUEST, cause);
    }
}
