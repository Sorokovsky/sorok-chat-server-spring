package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Помилка", description = "Данні помилки")
public record ErrorModel(
        @Schema(description = "Код помилки", requiredMode = Schema.RequiredMode.REQUIRED)
        int statusCode,
        @Schema(description = "Повідомлення помилки", requiredMode = Schema.RequiredMode.REQUIRED)
        String text
) {
}
