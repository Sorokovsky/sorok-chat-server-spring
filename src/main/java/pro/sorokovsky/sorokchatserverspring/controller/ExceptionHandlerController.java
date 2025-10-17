package pro.sorokovsky.sorokchatserverspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.sorokovsky.sorokchatserverspring.contract.ErrorModel;
import pro.sorokovsky.sorokchatserverspring.exception.base.HttpException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {
    private final MessageSource messageSource;

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorModel> handleNotFoundException(
            HttpException exception,
            Locale locale
    ) {
        final var arguments = new Object[]{exception.getEntity(), exception.getKey(), exception.getValue()};
        final var defaultMessage = messageSource.getMessage(exception.getMessageCode(), arguments, Locale.getDefault());
        final var message = messageSource.getMessage(exception.getMessageCode(), arguments, defaultMessage, locale);
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorModel(exception.getStatusCode().value(), message));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException exception) {
        final var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            final var key = ((FieldError) error).getField();
            final var value = error.getDefaultMessage();
            errors.put(key, value);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
