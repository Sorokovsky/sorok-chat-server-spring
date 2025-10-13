package pro.sorokovsky.sorokchatserverspring.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DefaultRefreshTokenFactory implements RefreshTokenFactory {
    private Duration lifetime = Duration.ofDays(7);

    @Override
    public Token apply(UserModel user) {
        final var now = Instant.now();
        return new Token(user.getEmail(), UUID.randomUUID(), now, now.plus(lifetime));
    }
}
