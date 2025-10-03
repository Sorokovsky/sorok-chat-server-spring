package pro.sorokovsky.sorokchatserverspring.constants;

import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Instant;
import java.util.Date;

public class UserSeeders {

    public static UserEntity getUserEntity() {
        final var now = Date.from(Instant.now());
        final var user = new UserEntity();
        user.setId(1L);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setEmail("Sorokovskys@ukr.net");
        user.setPassword("{hashed password}");
        user.setFirstName("Andrey");
        user.setLastName("Sorokovsky");
        user.setMiddleName("Ivanovich");
        return user;
    }

    public static UserModel getUserModel() {
        final var now = Date.from(Instant.now());
        final var user = new UserModel();
        user.setId(1L);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setEmail("Sorokovskys@ukr.net");
        user.setPassword("{hashed password}");
        user.setFirstName("Andrey");
        user.setLastName("Sorokovsky");
        user.setMiddleName("Ivanovich");
        return user;
    }

    public static NewUser getNewUser() {
        return new NewUser(
                "Sorokovskys@ukr.net",
                "{password}",
                "Andrey",
                "Sorokovsky",
                "Ivanovich"
        );
    }
}
