package pro.sorokovsky.sorokchatserverspring.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.service.AccessTokenStorage;
import pro.sorokovsky.sorokchatserverspring.service.RefreshTokenStorage;

import java.io.IOException;

@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final AccessTokenStorage accessTokenStorage;
    private final RefreshTokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final var refreshToken = refreshTokenStorage.getToken(request).orElse(null);
        if (refreshToken != null) {
            final var accessToken = accessTokenFactory.apply(refreshToken);
            accessTokenStorage.setToken(response, accessToken);
        } else {
            final var accessToken = accessTokenStorage.getToken(request).orElse(null);
            if (accessToken != null) accessTokenStorage.setToken(response, accessToken);
            else accessTokenStorage.clearToken(response);
        }
        filterChain.doFilter(request, response);
    }
}
