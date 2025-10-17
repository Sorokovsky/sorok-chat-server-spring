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
import pro.sorokovsky.sorokchatserverspring.converter.TokensAuthenticationConverter;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;

@Builder
public class TokensAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final AccessTokenStorage accessTokenStorage;

    @Override
    public void init(HttpSecurity builder) {
    }

    @Override
    public void configure(HttpSecurity builder) {
        final var converter = new TokensAuthenticationConverter(accessTokenStorage);
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        final var filter = new AuthenticationFilter(authenticationManager, converter);
        filter.setFailureHandler((request, response, exception) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        });
        filter.setSuccessHandler((_, _, _) -> {
        });
        builder.addFilterBefore(filter, AuthorizationFilter.class);
    }
}
