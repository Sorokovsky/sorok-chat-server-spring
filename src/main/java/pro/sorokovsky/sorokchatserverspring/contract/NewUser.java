package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Новий користувач", description = "Данні про нового користувача", requiredMode = Schema.RequiredMode.REQUIRED)
public record NewUser(
        @Email(message = "errors.not-email")
        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        @Schema(
                name = "Електронна адреса",
                description = "Електронна адреса нового користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Sorokovskys@ukr.net"
        )
        String email,

        @NotBlank(message = "errors.not-empty")
        @Size(min = 8, message = "{errors.size}")
        @NotNull(message = "errors.not-empty")
        @Schema(
                name = "Пароль",
                description = "Пароль нового користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "password"
        )
        String password,

        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        @Schema(
                name = "Ім'я",
                description = "Ім'я нового користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Андрій"
        )
        String firstName,

        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        @Schema(
                name = "Прізвище",
                description = "Прізвище нового користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Сороковський"
        )
        String lastName,

        @NotBlank(message = "errors.not-empty")
        @NotNull(message = "errors.not-empty")
        @Schema(
                name = "По батькові",
                description = "По батькові нового користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Іванович"
        )
        String middleName
) {
}
