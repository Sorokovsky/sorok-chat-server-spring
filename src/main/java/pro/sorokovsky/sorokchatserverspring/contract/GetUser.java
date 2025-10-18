package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Отриманий користувач", description = "Данні які отримає користувач про користувач", requiredMode = Schema.RequiredMode.REQUIRED)
public record GetUser(
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
                description = "Електронна адреса користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Sorokovskys@ukr.net"
        )
        String email,
        @Schema(
                description = "Ім'я користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Андрій"
        )
        String firstName,
        @Schema(
                description = "Прізвище користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Сороковський"
        )
        String lastName,
        @Schema(
                description = "По батькові користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Іванович"
        )
        String middleName,
        @Schema(
                description = "Ключ",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Ключ"
        )
        String macSecret
) {
}
