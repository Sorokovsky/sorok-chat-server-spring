package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Schema(name = "Отриманий чат", description = "Данні отриманого чату", requiredMode = Schema.RequiredMode.REQUIRED)
public record GetChannel(
        @Schema(
                description = "Ідентифікатор користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "1"
        )
        long id,
        @Schema(
                description = "Час та дата користувача",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Date createdAt,
        @Schema(
                description = "Час та оновлення користувача",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Date updatedAt,
        @Schema(
                description = "Назва чату",
                example = "Крутий чат",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,
        @Schema(
                description = "Опис чату",
                example = "Дуже крутий чат",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description,
        @ArraySchema(schema = @Schema(implementation = GetUser.class))
        List<GetUser> members,
        @ArraySchema(schema = @Schema(implementation = GetMessage.class))
        List<GetMessage> messages
) {

}
