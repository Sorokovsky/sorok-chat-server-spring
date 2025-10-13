package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.NotBlank;

public record NewStateMessage(
        @NotBlank(message = "errors.not-empty")
        String text
) {
}
