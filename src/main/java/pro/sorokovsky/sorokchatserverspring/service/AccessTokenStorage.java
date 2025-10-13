package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.deserializer.AccessTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.serializer.AccessTokenSerializer;

@RequiredArgsConstructor
@Service
public class AccessTokenStorage implements TokenStorage {
    private final AccessTokenSerializer serializer;
    private final AccessTokenDeserializer deserializer;

    @Override
    public Token getToken(HttpServletRequest request) {
        return null;
    }

    @Override
    public void setToken(HttpServletResponse response, Token token) {

    }

    @Override
    public void clearToken(HttpServletResponse response) {

    }
}
