package com.example.solveryInvest;

import com.example.solveryInvest.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.example.solveryInvest.service.encoderService.PasswordEncoderService.passwordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findUserByEmail;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        //Data access object, responsible for fetching UserDetails and encode password etc.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
