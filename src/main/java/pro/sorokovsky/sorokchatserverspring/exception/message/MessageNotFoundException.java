package pro.sorokovsky.sorokchatserverspring.exception.message;

import pro.sorokovsky.sorokchatserverspring.exception.base.NotFoundException;

public class MessageNotFoundException extends NotFoundException {
    private static final String ENTITY_NAME = "Message";

    public MessageNotFoundException(String key, String value) {
        super(ENTITY_NAME, key, value);
    }

    public MessageNotFoundException(String key, String value, Throwable cause) {
        super(ENTITY_NAME, key, value, cause);
    }
}
