package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.text.ParseException;

@RequiredArgsConstructor
public class RefreshTokenDeserializer extends JwtTokenDeserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenDeserializer.class);
    private final JWEDecrypter decrypter;

    @Override
    public Token apply(String string) {
        try {
            final var encrypted = EncryptedJWT.parse(string);
            encrypted.decrypt(decrypter);
            return extractClaims(encrypted.getJWTClaimsSet());
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return null;
        } catch (JOSEException exception) {
            LOGGER.warn(exception.getMessage(), exception);
            return null;
        }
    }
}
