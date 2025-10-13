package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.text.ParseException;

@RequiredArgsConstructor
public class AccessTokenDeserializer extends JwtTokenDeserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenDeserializer.class);
    private final JWSVerifier verifier;

    @Override
    public Token apply(String string) {
        try {
            final var signed = SignedJWT.parse(string);
            signed.verify(verifier);
            return extractClaims(signed.getJWTClaimsSet());
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return null;
        } catch (JOSEException exception) {
            LOGGER.info(exception.getMessage(), exception);
            return null;
        }
    }
}
