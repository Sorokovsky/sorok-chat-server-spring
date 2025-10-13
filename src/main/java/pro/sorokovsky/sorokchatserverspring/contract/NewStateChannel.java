package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.NotBlank;

public record NewStateChannel(
        @NotBlank(message = "errors.not-empty")
        String name,

        @NotBlank(message = "errors.not-empty")
        String description
) {
}
