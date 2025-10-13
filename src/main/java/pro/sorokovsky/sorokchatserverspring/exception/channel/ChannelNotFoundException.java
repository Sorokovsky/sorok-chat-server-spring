package pro.sorokovsky.sorokchatserverspring.exception.channel;

import pro.sorokovsky.sorokchatserverspring.exception.base.NotFoundException;

public class ChannelNotFoundException extends NotFoundException {
    private static final String ENTITY = "User";

    public ChannelNotFoundException(String key, String value) {
        super(ENTITY, key, value);
    }

    public ChannelNotFoundException(String key, String value, Throwable cause) {
        super(ENTITY, key, value, cause);
    }
}
