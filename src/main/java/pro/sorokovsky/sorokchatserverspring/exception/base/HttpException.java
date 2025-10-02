package pro.sorokovsky.sorokchatserverspring.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpException extends RuntimeException {
    private final HttpStatus statusCode;

    public HttpException(String entity, String key, String value, String word, HttpStatus statusCode) {
        super(generateMessage(entity, key, word, value));
        this.statusCode = statusCode;
    }

    public HttpException(String entity, String key, String value, String word, HttpStatus statusCode, Throwable cause) {
        super(generateMessage(entity, key, word, value), cause);
        this.statusCode = statusCode;
    }

    private static String generateMessage(String entity, String key, String word, String value) {
        return "%s %s exists with %s = %s".formatted(entity, key, word, value);
    }
}
