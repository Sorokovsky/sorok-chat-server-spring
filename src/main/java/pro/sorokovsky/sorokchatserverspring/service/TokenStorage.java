package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.Optional;

public interface TokenStorage {
    Optional<Token> getToken(HttpServletRequest request);

    void setToken(HttpServletResponse response, Token token);

    void clearToken(HttpServletResponse response);
}
