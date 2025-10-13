package pro.sorokovsky.sorokchatserverspring.factory;

import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.util.function.Function;

public interface RefreshTokenFactory extends Function<UserModel, Token> {
}
