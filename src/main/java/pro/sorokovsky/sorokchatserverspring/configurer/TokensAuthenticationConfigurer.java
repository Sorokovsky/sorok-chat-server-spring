package pro.sorokovsky.sorokchatserverspring.configurer;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import pro.sorokovsky.sorokchatserverspring.converter.TokensAuthenticationConverter;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.filter.RefreshTokenFilter;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;
import pro.sorokovsky.sorokchatserverspring.service.RefreshTokenStorage;

@Builder
public class TokensAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final AccessTokenStorage accessTokenStorage;
    private final RefreshTokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;

    @Override
    public void init(HttpSecurity builder) {
    }

    @Override
    public void configure(HttpSecurity builder) {
        final var converter = new TokensAuthenticationConverter(accessTokenStorage);
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        final var authenticationFilter = new AuthenticationFilter(authenticationManager, converter);
        final var refreshTokenFilter = new RefreshTokenFilter(accessTokenStorage, refreshTokenStorage, accessTokenFactory);
        authenticationFilter.setFailureHandler((_, response, _) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        });
        authenticationFilter.setSuccessHandler((_, _, _) -> {
        });
        builder.addFilterBefore(authenticationFilter, AuthorizationFilter.class);
        builder.addFilterAfter(refreshTokenFilter, DisableEncodeUrlFilter.class);
    }
}
