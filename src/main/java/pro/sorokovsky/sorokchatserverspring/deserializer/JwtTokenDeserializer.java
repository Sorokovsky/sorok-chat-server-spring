package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.UUID;

public abstract class JwtTokenDeserializer implements TokenDeserializer {
    protected Token extractClaims(JWTClaimsSet claims) {
        return new Token(
                claims.getSubject(),
                UUID.fromString(claims.getJWTID()),
                claims.getIssueTime().toInstant(),
                claims.getExpirationTime().toInstant()
        );
    }
}
