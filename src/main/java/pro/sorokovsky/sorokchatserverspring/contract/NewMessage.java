package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewMessage(
        @NotNull(message = "errors.not-empty")
        @NotBlank(message = "errors.not-empty")
        String text
) {
}
