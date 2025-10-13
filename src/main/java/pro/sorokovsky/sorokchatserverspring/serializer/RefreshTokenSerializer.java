package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.base.InvalidErrorException;

@RequiredArgsConstructor
public class RefreshTokenSerializer extends JwtTokenSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenSerializer.class);
    private final JWEAlgorithm algorithm;
    private final EncryptionMethod method;
    private final JWEEncrypter encrypter;

    @Override
    public String apply(Token token) {
        final var jweHeader = new JWEHeader.Builder(algorithm, method)
                .keyID(token.id().toString())
                .build();
        final var encrypted = new EncryptedJWT(jweHeader, generateClaims(token));
        try {
            encrypted.encrypt(encrypter);
            return encrypted.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new InvalidErrorException(exception.getMessage());
        }
    }
}
