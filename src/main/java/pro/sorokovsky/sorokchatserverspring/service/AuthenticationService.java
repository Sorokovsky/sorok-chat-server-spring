package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pro.sorokovsky.sorokchatserverspring.contract.LoginDto;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.exception.base.InvalidErrorException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.strategy.TokensSessionAuthenticationStrategy;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersService usersService;
    private final TokensSessionAuthenticationStrategy authenticationStrategy;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenStorage accessTokenStorage;
    private final RefreshTokenStorage refreshTokenStorage;

    public UserModel register(NewUser newUser) {
        final var createdUser = usersService.create(newUser);
        authenticate(newUser.email(), newUser.password());
        return createdUser;
    }

    private void authenticate(String email, String password) {
        final var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) throw new InvalidErrorException("Invalid request");
        final var request = attributes.getRequest();
        final var response = attributes.getResponse();
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        final var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authenticationStrategy.onAuthentication(authentication, request, response);
    }

    public void login(LoginDto loginDto) {
        final var candidate = usersService.getByEmail(loginDto.email())
                .orElseThrow(() -> new UserNotFoundException("email", loginDto.email()));
        if (passwordEncoder.matches(loginDto.password(), candidate.getPassword())) {
            authenticate(loginDto.email(), loginDto.password());
        }
    }

    public void logout() {
        final var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) throw new InvalidErrorException("Invalid request");
        final var response = attributes.getResponse();
        if (response == null) throw new InvalidErrorException("Invalid response");
        accessTokenStorage.clearToken(response);
        refreshTokenStorage.clearToken(response);
    }
}
