package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import com.example.solveryInvest.exception.AlreadyExistsException;
import com.example.solveryInvest.security.JwtService;
import com.example.solveryInvest.service.userService.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("auth-service")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    @Override
    public AuthenticationResponse register(UserDto userDto) {
        validateEmail(userDto.getEmail());
        var user = userService.save(userDto);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(UserDto userDto) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );
        var user = userService.findUserByEmail(userDto.getEmail());
        var jwtToken = jwtService.generateToken(user);
        log.info("User {} with email {} is authenticated", userDto.getFirstName(), userDto.getEmail());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private void validateEmail(String email) {
        if (userService.emailExists(email)) {
            throw new AlreadyExistsException(String.format("User with email %s already exists", email));
        }
    }
}
