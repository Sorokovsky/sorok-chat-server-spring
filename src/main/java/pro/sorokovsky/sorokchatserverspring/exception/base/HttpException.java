package pro.sorokovsky.sorokchatserverspring.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String entity;
    private final String key;
    private final String value;
    private final String messageCode;

    public HttpException(String entity, String key, String value, String messageCode, HttpStatus statusCode) {
        super(messageCode);
        this.entity = entity;
        this.statusCode = statusCode;
        this.key = key;
        this.value = value;
        this.messageCode = messageCode;
    }

    public HttpException(String entity, String key, String value, String messageCode, HttpStatus statusCode, Throwable cause) {
        super(messageCode, cause);
        this.entity = entity;
        this.statusCode = statusCode;
        this.key = key;
        this.value = value;
        this.messageCode = messageCode;
    }
}
