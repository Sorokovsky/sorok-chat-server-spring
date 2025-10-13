package pro.sorokovsky.sorokchatserverspring.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Component
public class DefaultAccessTokenFactory implements AccessTokenFactory {
    private Duration lifetime = Duration.ofMinutes(15);

    @Override
    public Token apply(Token token) {
        final var now = Instant.now();
        return new Token(token.subject(), UUID.randomUUID(), now, now.plus(lifetime));
    }
}
