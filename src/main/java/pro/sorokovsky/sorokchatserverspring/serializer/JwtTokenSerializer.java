package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.Date;

public abstract class JwtTokenSerializer implements TokenSerializer {
    protected JWTClaimsSet generateClaims(Token token) {
        return new JWTClaimsSet.Builder()
                .subject(token.subject())
                .jwtID(token.id().toString())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .build();
    }
}
