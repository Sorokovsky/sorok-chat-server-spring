package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Авторизаційні данні", description = "Данні для авторизації", requiredMode = Schema.RequiredMode.REQUIRED)
public record LoginDto(
        @Email(message = "errors.not-email")
        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "Електронна адреса",
                defaultValue = "Sorokovskys@ukr.net"
        )
        String email,

        @NotBlank(message = "errors.not-empty")
        @Size(min = 8, message = "{errors.size}")
        @NotNull(message = "errors.not-empty")
        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                description = "Пароль",
                defaultValue = "password"
        )
        String password
) {
}
