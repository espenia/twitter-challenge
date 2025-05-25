package twitter.challenge.espenia.core.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.password.PasswordEncoder;
import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.core.exception.BadRequestException;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.UserResponse;
import twitter.challenge.espenia.core.usecase.request.UserRequest;
import twitter.challenge.espenia.core.usecase.request.UserUpdateRequest;
import twitter.challenge.espenia.core.usecase.validator.ValidateUser;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest extends UnitTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserGateway userGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Spy
    private ValidateUser validateUser;

    @BeforeEach
    void setUp() {
        userService = new UserService(userGateway, passwordEncoder, validateUser);
    }

    @Test
    void testCreateUser() {
        // given
        UserRequest user = Factory. sampleUserCreateRequest();
        when(userGateway.create(any(User.class))).thenReturn(Factory.sampleUser());
        when(userGateway.existsByUsername(anyString())).thenReturn(false);
        when(userGateway.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // when
        UserResponse response = userService.create(user);
        // then
        assertEquals(Factory.USERNAME, response.username());
        assertEquals(Factory.EMAIL, response.email());
        assertEquals(Factory.USERID, response.id());
        assertEquals("Esteban", response.displayName());
        assertEquals("This is my bio", response.bio());


        verify(userGateway).create(any(User.class));
        verify(validateUser).execute(user);
        verify(passwordEncoder).encode(anyString());
    }

    @Test
    void testCreateUserWithExistingUsername() {
        // given
        UserRequest user = Factory.sampleUserCreateRequest();
        when(userGateway.existsByUsername(anyString())).thenReturn(true);
        // when
        var excp = assertThrows(BadRequestException.class, () -> userService.create(user));
        // then
        assertEquals(String.format("Username already exists: %s", Factory.USERNAME), excp.getMessage());

        verify(userGateway).existsByUsername(anyString());
    }

    @Test
    void testCreateUserWithExistingEmail() {
        // given
        UserRequest user = Factory.sampleUserCreateRequest();
        when(userGateway.existsByUsername(anyString())).thenReturn(false);
        when(userGateway.existsByEmail(anyString())).thenReturn(true);
        // when
        var excp = assertThrows(BadRequestException.class, () -> userService.create(user));
        // then
        assertEquals(String.format("Email already exists: %s", Factory.EMAIL), excp.getMessage());

        verify(userGateway).existsByUsername(anyString());
    }

    @Test
    void testUpdateUser() {
        // given
        UserUpdateRequest user = Factory.sampleUserUpdateRequest();
        when(userGateway.findById(Factory.USERID)).thenReturn(Factory.sampleUser());
        when(userGateway.update(any(User.class))).thenReturn(Factory.updatedUser());
        // when
        UserResponse response = userService.updateUser(Factory.USERID, user);
        // then
        assertEquals(Factory.USERNAME, response.username());
        assertEquals(Factory.EMAIL, response.email());
        assertEquals(Factory.USERID, response.id());
        assertEquals("Tata", response.displayName());
        assertEquals("This is my new bio", response.bio());
        verify(userGateway).update(any(User.class));
    }

    // add test for gets


}
