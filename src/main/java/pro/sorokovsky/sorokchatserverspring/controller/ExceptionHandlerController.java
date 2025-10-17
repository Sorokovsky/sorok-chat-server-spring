package pro.sorokovsky.sorokchatserverspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.sorokovsky.sorokchatserverspring.contract.ErrorModel;
import pro.sorokovsky.sorokchatserverspring.exception.base.HttpException;

import java.util.Locale;

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
}
