package pro.sorokovsky.sorokchatserverspring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import pro.sorokovsky.sorokchatserverspring.configurer.TokensAuthenticationConfigurer;
import pro.sorokovsky.sorokchatserverspring.provider.TokensAuthenticationService;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;
import pro.sorokovsky.sorokchatserverspring.strategy.TokensSessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            TokensSessionAuthenticationStrategy authenticationStrategy,
            AuthenticationProvider authenticationProvider,
            TokensAuthenticationConfigurer tokensAuthenticationConfigurer
    ) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(authenticationStrategy));
        http.apply(tokensAuthenticationConfigurer);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public TokensAuthenticationConfigurer tokensAuthenticationConfigurer(AccessTokenStorage accessTokenStorage) {
        return TokensAuthenticationConfigurer.builder()
                .accessTokenStorage(accessTokenStorage)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(TokensAuthenticationService tokensAuthenticationService) {
        final var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(tokensAuthenticationService);
        return provider;
    }
}
