package pro.sorokovsky.sorokchatserverspring.exception.user;

import org.springframework.http.HttpStatus;
import pro.sorokovsky.sorokchatserverspring.exception.base.AlreadyExistsException;

public class UserAlreadyExistsException extends AlreadyExistsException {
    private static final String ENTITY_NAME = "User";

    public UserAlreadyExistsException(String key, String value) {
        super(ENTITY_NAME, key, value, HttpStatus.BAD_REQUEST);
    }

    public UserAlreadyExistsException(String key, String value, Throwable cause) {
        super(ENTITY_NAME, key, value, HttpStatus.BAD_REQUEST, cause);
    }
}
