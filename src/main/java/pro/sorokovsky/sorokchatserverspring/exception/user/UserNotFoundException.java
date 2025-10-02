package pro.sorokovsky.sorokchatserverspring.exception.user;

import pro.sorokovsky.sorokchatserverspring.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String ENTITY = "User";

    public UserNotFoundException(String key, String value) {
        super(ENTITY, key, value);
    }

    public UserNotFoundException(String key, String value, Throwable cause) {
        super(ENTITY, key, value, cause);
    }
}
