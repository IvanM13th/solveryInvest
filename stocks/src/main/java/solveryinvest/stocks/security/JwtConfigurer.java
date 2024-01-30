package solveryinvest.stocks.security;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtService jwtService;
    private final HazelcastInstance hazelcastInstance;

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthFilter customFilter = new JwtAuthFilter(jwtService, hazelcastInstance);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

