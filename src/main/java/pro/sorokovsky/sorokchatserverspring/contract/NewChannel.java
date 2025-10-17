package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Новий чат", description = "Данні для нового чату", requiredMode = Schema.RequiredMode.REQUIRED)
public record NewChannel(
        @NotNull(message = "errors.not-empty")
        @NotBlank(message = "errors.not-empty")
        @Schema(
                name = "Ім'я",
                description = "Ім'я для нового чату",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Крутий чат"
        )
        String name,

        @NotBlank(message = "errors.not-empty")
        @Schema(
                name = "Опис",
                description = "Опис нового чату",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Дуже крутий чат"
        )
        String description
) {
}
