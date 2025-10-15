package pro.sorokovsky.sorokchatserverspring.strategy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;
import pro.sorokovsky.sorokchatserverspring.service.RefreshTokenStorage;

@Component
@RequiredArgsConstructor
public class TokensSessionAuthenticationStrategy implements SessionAuthenticationStrategy {
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;
    private final AccessTokenStorage accessTokenStorage;
    private final RefreshTokenStorage refreshTokenStorage;

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
            throws SessionAuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            final var refreshToken = refreshTokenFactory.apply(authenticationToken);
            final var accessToken = accessTokenFactory.apply(refreshToken);
            accessTokenStorage.setToken(response, accessToken);
            refreshTokenStorage.setToken(response, refreshToken);
        }
    }
}