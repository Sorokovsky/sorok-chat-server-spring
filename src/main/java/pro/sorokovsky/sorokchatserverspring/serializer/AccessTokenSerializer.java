package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.base.InvalidErrorException;

@RequiredArgsConstructor
public class AccessTokenSerializer extends JwtTokenSerializer {
    private final JWSAlgorithm algorithm;
    private final JWSSigner signer;
    private final Logger LOGGER = LoggerFactory.getLogger(AccessTokenSerializer.class);

    @Override
    public String apply(Token token) {
        final var jwsHeader = new JWSHeader.Builder(algorithm)
                .keyID(token.id().toString())
                .build();
        final var claims = generateClaims(token);
        final var signed = new SignedJWT(jwsHeader, claims);
        try {
            signed.sign(signer);
            return signed.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new InvalidErrorException(exception.getMessage());
        }
    }
}
