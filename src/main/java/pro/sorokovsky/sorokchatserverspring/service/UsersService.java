package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateUser;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserAlreadyExistsException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository repository;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public Optional<UserModel> getById(long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public Optional<UserModel> getByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toModel);
    }

    @Transactional
    public UserModel create(NewUser newUser) {
        final var candidate = getByEmail(newUser.email()).orElse(null);
        if (candidate != null) throw new UserAlreadyExistsException("email", newUser.email());
        return mapper.toModel(repository.save(mapper.toEntity(newUser)));
    }

    @Transactional
    public UserModel update(Long id, NewStateUser newState) {
        final var user = getById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        var mergedState = mapper.merge(user, newState);
        return mapper.toModel(repository.save(mergedState));
    }

    @Transactional
    public UserModel deleteById(Long id) {
        final var user = getById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        repository.deleteById(id);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}