package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Нове повідомлення", description = "Данні про нове повідомлення", requiredMode = Schema.RequiredMode.REQUIRED)
public record NewMessage(
        @NotNull(message = "errors.not-empty")
        @NotBlank(message = "errors.not-empty")
        @Schema(
                name = "Текст",
                description = "Зміст нового повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Привіт!"
        )
        String text
) {
}
