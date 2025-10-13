package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.deserializer.AccessTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.serializer.AccessTokenSerializer;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccessTokenStorage implements TokenStorage {
    private static final String BEARER = "Bearer ";

    private final AccessTokenSerializer serializer;
    private final AccessTokenDeserializer deserializer;

    @Override
    public Optional<Token> getToken(HttpServletRequest request) {
        final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER)) return Optional.empty();
        return Optional.ofNullable(deserializer.apply(header.replace(BEARER, "")));
    }

    @Override
    public void setToken(HttpServletResponse response, Token token) {
        final var stringToken = serializer.apply(token);
        response.setHeader(HttpHeaders.AUTHORIZATION, stringToken);
    }

    @Override
    public void clearToken(HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}
