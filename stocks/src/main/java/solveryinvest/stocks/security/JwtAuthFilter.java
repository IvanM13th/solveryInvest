package solveryinvest.stocks.security;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final HazelcastInstance hazelcastInstance;

    @Value("${cookie.hc.sess}")
    private String hcSession;

    @Value("${cookie.jwt}")
    private String jwt;

    @Override
    protected void doFilterInternal(
            //we can intercept every request and get data from it
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            //contains list of other filters that we need to execute
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        var cookie = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        final String jwtToken;
        final String sessionId;
        var cookieMap = Arrays.stream(cookie)
                .filter(c -> Objects.equals(c.getName(), hcSession) || Objects.equals(c.getName(), jwt))
                .collect(Collectors.toMap(Cookie::getName, Function.identity()));
        if (!cookieMap.containsKey(hcSession) || !cookieMap.containsKey(jwt)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            jwtToken = cookieMap.get(jwt).getValue();
            sessionId = cookieMap.get(hcSession).getValue();
        }
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            IMap<String, MapSession> sessionsImap = hazelcastInstance.getMap(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
            var session = sessionsImap.get(sessionId);
            if (Objects.nonNull(session) && !session.isExpired()) {
                session.setLastAccessedTime(Instant.now());
                var user = jwtService.getAuthentication(jwtToken);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        jwt,
                        user.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        filterChain.doFilter(request, response);
    }
}
