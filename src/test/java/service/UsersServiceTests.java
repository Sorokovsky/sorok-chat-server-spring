package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.repository.UsersRepository;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static pro.sorokovsky.sorokchatserverspring.constants.UserSeeders.getUserEntity;
import static pro.sorokovsky.sorokchatserverspring.constants.UserSeeders.getUserModel;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UsersService usersService;

    @Test
    public void findById_ifFound_returnsUser() {
        //given
        final var id = 1L;
        final var expectedModel = Optional.of(getUserModel());
        final var expectedEntity = Optional.of(getUserEntity());
        doReturn(expectedModel.get()).when(userMapper).toModel(expectedEntity.get());
        doReturn(expectedEntity).when(usersRepository).findById(id);

        //when
        final var result = usersService.getById(id);

        //then
        assertTrue(result.isPresent());
        assertEquals(expectedModel.get(), result.get());
    }

    @Test
    public void findById_ifNotFound_returnsEmptyOptional() {
        //given
        final var id = 2L;
        doReturn(Optional.empty()).when(usersRepository).findById(id);

        //when
        final var result = usersService.getById(id);

        //then
        assertTrue(result.isEmpty());

    }

    @Test
    public void findByEmail_ifFound_returnsUser() {
        //given
        final var email = "Sorokovskys@ukr.net";
        final var expectedModel = Optional.of(getUserModel());
        final var expectedEntity = Optional.of(getUserEntity());
        doReturn(expectedModel.get()).when(userMapper).toModel(expectedEntity.get());
        doReturn(expectedEntity).when(usersRepository).findByEmail(email);

        //when
        final var result = usersService.getByEmail(email);

        //then
        assertTrue(result.isPresent());
        assertEquals(expectedModel.get(), result.get());
    }

    @Test
    public void findByEmail_ifNotFound_returnsEmptyOptional() {
        //given
        final var email = "Sorokovskys@gmail.com";
        doReturn(Optional.empty()).when(usersRepository).findByEmail(email);

        //when
        final var result = usersService.getByEmail(email);

        //then
        assertTrue(result.isEmpty());

    }
}
