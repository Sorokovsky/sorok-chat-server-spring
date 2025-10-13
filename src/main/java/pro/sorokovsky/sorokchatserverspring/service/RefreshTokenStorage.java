package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.deserializer.RefreshTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.serializer.RefreshTokenSerializer;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RefreshTokenStorage implements TokenStorage {
    private final static String COOKIE_NAME = "__Host-16SbmXqCJAfwzMiF8v2jJxnWWW2lVHV6";

    private final RefreshTokenSerializer serializer;
    private final RefreshTokenDeserializer deserializer;

    private static void setCookie(HttpServletResponse response, String value, int maxAge) {
        final var cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setDomain(null);
        response.addCookie(cookie);
    }

    @Override
    public Optional<Token> getToken(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst().map(cookie -> deserializer.apply(cookie.getValue()));
    }

    @Override
    public void setToken(HttpServletResponse response, Token token) {
        final var maxAge = (int) ChronoUnit.SECONDS.between(token.createdAt(), token.expiresAt());
        setCookie(response, serializer.apply(token), maxAge);
    }

    @Override
    public void clearToken(HttpServletResponse response) {
        setCookie(response, "", 0);
    }
}
