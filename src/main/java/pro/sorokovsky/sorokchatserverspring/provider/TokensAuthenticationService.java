package pro.sorokovsky.sorokchatserverspring.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

@Component
@RequiredArgsConstructor
public class TokensAuthenticationService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final UserDetailsService userDetailsService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        final var accessToken = (Token) token.getPrincipal();
        return userDetailsService.loadUserByUsername(accessToken.subject());
    }
}
