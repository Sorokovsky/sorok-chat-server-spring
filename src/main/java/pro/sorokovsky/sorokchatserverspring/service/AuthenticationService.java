package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;

    public UserModel register(NewUser newUser) {
        final var createdUser = usersService.create(newUser);
        authenticate(newUser.email(), newUser.password());
        return createdUser;
    }

    private void authenticate(String email, String password) {
        final var userNameAuthenticatedToken = new UsernamePasswordAuthenticationToken(email, password);
        final var authenticationToken = authenticationManager.authenticate(userNameAuthenticatedToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
