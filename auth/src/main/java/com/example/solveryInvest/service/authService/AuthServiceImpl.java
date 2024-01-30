package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.AuthDto;
import com.example.solveryInvest.dto.HazelcastUserData;
import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import com.example.solveryInvest.exception.AlreadyExistsException;
import com.example.solveryInvest.security.JwtService;
import com.example.solveryInvest.service.userService.UserService;
import com.hazelcast.core.HazelcastInstance;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service("auth-service")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authManager;

    @Value("${cookie.hc.sess}")
    private String hcSession;

    @Value("${cookie.jwt}")
    private String jwt;

    @Override
    public AuthDto register(HttpServletRequest request, HttpServletResponse response, UserDto userDto) {
        validateEmail(userDto.getEmail());
        var user = userService.save(userDto);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse.builder().token(jwtToken).build();
        putSessionInfoToHazelcast(request, response, jwtToken);
        return AuthDto.builder()
                .user(modelMapper.map(user, UserDto.class))
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthDto authenticate(HttpServletRequest request, HttpServletResponse response, UserDto userDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );
        var user = userService.findUserByEmail(userDto.getEmail());
        var jwtToken = jwtService.generateToken(user);
        log.info("User {} with email {} is authenticated", userDto.getFirstName(), userDto.getEmail());
        putSessionInfoToHazelcast(request, response, jwtToken);
        return AuthDto.builder()
                .user(modelMapper.map(user, UserDto.class))
                .token(jwtToken)
                .build();
    }

    private void validateEmail(String email) {
        if (userService.emailExists(email)) {
            throw new AlreadyExistsException(String.format("User with email %s already exists", email));
        }
    }

    private void putSessionInfoToHazelcast(HttpServletRequest request, HttpServletResponse response, String jwtToken) {
        final var id = request.getSession().getId();
        final var sess = new Cookie(hcSession, id);
        final var jwtCookie = new Cookie(jwt, jwtToken);
        jwtCookie.setPath("/");
        sess.setPath("/");
        response.addCookie(jwtCookie);
        response.addCookie(sess);
    }
}
