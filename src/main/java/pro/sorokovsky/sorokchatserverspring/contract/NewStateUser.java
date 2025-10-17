package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Оновлений користувач", description = "Данні для оновлення користувача", requiredMode = Schema.RequiredMode.REQUIRED)
public record NewStateUser(
        @Email(message = "errors.not-email")
        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Нова електронна адреса",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Sorokovskys@ukr.net"
        )
        String email,

        @NotBlank(message = "errors.not-empty")
        @Size(min = 8, message = "{errors.size}")
        @Schema(
                description = "Новий пароль",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "password"
        )
        String password,

        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Нове ім'я",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Андрій"
        )
        String firstName,

        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Нове прізвище",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Сороковський"
        )
        String lastName,

        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Нове по батькові",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Іванович"
        )
        String middleName
) {
}
