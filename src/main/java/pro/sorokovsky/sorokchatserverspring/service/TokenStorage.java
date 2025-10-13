package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

public interface TokenStorage {
    Token getToken(HttpServletRequest request);

    void setToken(HttpServletResponse response, Token token);

    void clearToken(HttpServletResponse response);
}
