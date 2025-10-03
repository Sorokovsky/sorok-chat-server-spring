package pro.sorokovsky.sorokchatserverspring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static pro.sorokovsky.sorokchatserverspring.constants.UserSeeders.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void toModel_shouldSuccessReturnModel() {
        //given
        final var entity = getUserEntity();
        final var expected = getUserModel();

        //when
        final var result = userMapper.toModel(entity);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void toEntity_fromNewUser_shouldReturnSuccessEntity() {
        //given
        final var newUser = getNewUser();
        final var expected = getUserEntity();
        expected.setId(null);
        doReturn(expected.getPassword()).when(passwordEncoder).encode(newUser.password());
        //when
        final var result = userMapper.toEntity(newUser);
        expected.setCreatedAt(result.getCreatedAt());
        expected.setUpdatedAt(result.getUpdatedAt());

        //then
        assertEquals(expected, result);
    }

    @Test
    public void merge_shouldSuccess() {
        //given
        final var oldState = getUserModel();
        final var newStateUser = getNewStateUser();
        final var expected = getUserEntity();
        doReturn(expected.getPassword()).when(passwordEncoder).encode(newStateUser.password());

        //when
        final var result = userMapper.merge(oldState, newStateUser);
        expected.setCreatedAt(result.getCreatedAt());
        expected.setUpdatedAt(result.getUpdatedAt());

        //given
        assertEquals(expected, result);
    }
}
