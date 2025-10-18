package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Оновлене повідомлене", description = "Данні про нове повідомлення")
public record NewStateMessage(
        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Новий текст повідомлення",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Вітаю у крутому чаті!"
        )
        String text,

        @NotNull(message = "errors.not-empty")
        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Підпис повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Привіт!"
        )
        String mac
) {
}
