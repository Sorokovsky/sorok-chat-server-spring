package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateUser;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    private static String chooseString(String oldString, String newString) {
        if (newString == null || newString.isBlank()) return oldString;
        return newString;
    }

    public UserModel toModel(UserEntity entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setMiddleName(entity.getMiddleName());
        return model;
    }

    public UserEntity toEntity(NewUser newUser) {
        final var now = Date.from(Instant.now());
        final var hashedPassword = passwordEncoder.encode(newUser.password());
        final var entity = new UserEntity();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setEmail(newUser.email());
        entity.setPassword(hashedPassword);
        entity.setFirstName(newUser.firstName());
        entity.setLastName(newUser.lastName());
        entity.setMiddleName(newUser.middleName());
        return entity;
    }

    public UserEntity merge(UserModel oldState, NewStateUser newState) {
        final var result = new UserEntity();
        result.setId(oldState.getId());
        result.setCreatedAt(oldState.getCreatedAt());
        result.setUpdatedAt(Date.from(Instant.now()));
        result.setEmail(chooseString(oldState.getEmail(), newState.email()));
        result.setPassword(chooseString(oldState.getPassword(), passwordEncoder.encode(newState.password())));
        result.setFirstName(chooseString(oldState.getFirstName(), newState.firstName()));
        result.setLastName(chooseString(oldState.getLastName(), newState.lastName()));
        result.setMiddleName(chooseString(oldState.getMiddleName(), newState.middleName()));
        return result;
    }

    public UserEntity toEntity(UserModel model) {
        final var entity = new UserEntity();
        entity.setId(model.getId());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setMiddleName(model.getMiddleName());
        return entity;
    }
}