package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewChannel(
        @NotNull(message = "errors.not-empty")
        @NotBlank(message = "errors.not-empty")
        String name,

        @NotBlank(message = "errors.not-empty")
        String description
) {
}
