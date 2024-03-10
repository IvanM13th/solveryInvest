package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.AuthDto;
import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.User;
import com.example.solveryInvest.entity.enums.Role;
import com.example.solveryInvest.exception.AlreadyExistsException;
import com.example.solveryInvest.exception.NotFoundException;
import com.example.solveryInvest.security.JwtService;
import com.example.solveryInvest.service.userService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Ivanov";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final Role ROLE = Role.GUEST;
    private static final String JWT = "jwtToken";

    private static final Long ID = 1L;


    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        userService = mock(UserService.class);
        jwtService = mock(JwtService.class);
    }

    @Test
    void testRegister_ValidUser_ReturnsAuthDto() {
        UserDto userDto = UserDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE)
                .build();
        User user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE);
        when(userService.save(userDto)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(JWT);
        AuthDto authDto = new AuthDto(userDto, JWT);
        when(authService.register(null, null, userDto)).thenReturn(authDto);
        assertEquals(userDto.getEmail(), authDto.getUser().getEmail());
        assertNotNull(authDto.getToken());
        assertEquals(JWT, authDto.getToken());
    }

    @Test
    void whenEmailExistsThenThrowException() {
        UserDto userDto = UserDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email("wrong_email")
                .password(PASSWORD)
                .role(ROLE)
                .build();
        when(authService.register(null, null, userDto)).thenThrow(AlreadyExistsException.class);
        assertThrows(AlreadyExistsException.class, () -> authService.register(null, null, userDto));
    }

    @Test
    void whenValidLoginAndPasswordThenLogin() {
        UserDto userDto = UserDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        User user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE);
        AuthDto authDto = new AuthDto(userDto, JWT);

        when(userService.findUserByEmail(EMAIL)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(JWT);
        when(authService.authenticate(null, null, userDto)).thenReturn(authDto);

        assertEquals(userDto.getEmail(), authDto.getUser().getEmail());
        assertNotNull(authDto.getToken());
        assertEquals(JWT, authDto.getToken());
    }

    @Test void whenInvalidLoginEmailThenThrowException() {
        UserDto userDto = UserDto.builder()
                .email("wrong email")
                .password(PASSWORD)
                .build();
        when(authService.authenticate(null, null, userDto)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> authService.authenticate(null, null, userDto));
    }
}