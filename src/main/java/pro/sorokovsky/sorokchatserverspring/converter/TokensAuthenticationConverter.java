package pro.sorokovsky.sorokchatserverspring.converter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;

@RequiredArgsConstructor
public class TokensAuthenticationConverter implements AuthenticationConverter {
    private final AccessTokenStorage accessTokenStorage;

    @Override
    public Authentication convert(HttpServletRequest request) {
        final var accessToken = accessTokenStorage.getToken(request).orElse(null);
        if (accessToken != null) return new PreAuthenticatedAuthenticationToken(accessToken, accessToken);
        return null;
    }
}
