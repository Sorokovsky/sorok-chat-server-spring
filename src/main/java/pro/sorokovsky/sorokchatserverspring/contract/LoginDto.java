package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @Email(message = "errors.not-email")
        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        String email,

        @NotBlank(message = "errors.not-empty")
        @Size(min = 8, message = "{errors.size}")
        @NotNull(message = "errors.not-empty")
        String password
) {
}
