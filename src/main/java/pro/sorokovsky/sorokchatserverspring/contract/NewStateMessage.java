package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Оновлене повідомлене", description = "Данні про нове повідомлення")
public record NewStateMessage(
        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Новий текст повідомлення",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Вітаю у крутому чаті!"
        )
        String text
) {
}
