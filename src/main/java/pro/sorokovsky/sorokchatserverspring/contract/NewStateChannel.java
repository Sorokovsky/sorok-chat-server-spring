package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Оновлений чат", description = "Данні для оновлення чату", requiredMode = Schema.RequiredMode.REQUIRED)
public record NewStateChannel(
        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Нова назва чату",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Супер крутий чат"
        )
        String name,

        @NotBlank(message = "errors.not-empty")
        @Schema(
                description = "Новий опис чату",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Супер дуже крутий чат"
        )
        String description
) {
}
