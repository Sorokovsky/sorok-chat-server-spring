package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Отримане повідомлення", description = "Данні отриманого повідомлення", requiredMode = Schema.RequiredMode.REQUIRED)
public record GetMessage(
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
                description = "Текст повідомлення",
                example = "Привіт!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String text,
        @Schema(implementation = GetUser.class)
        GetUser author
) {
}
