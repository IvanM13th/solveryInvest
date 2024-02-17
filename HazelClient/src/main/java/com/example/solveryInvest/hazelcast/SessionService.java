package com.example.solveryInvest.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final HazelcastInstance hazelcastInstance;
    public String getSession(HttpServletRequest request) {
        IMap<String, MapSession> sessionsImap = hazelcastInstance.getMap(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
        var cookieSessionId = request.getCookies()[1].getValue();
        var smth = sessionsImap.get(cookieSessionId);
        //smth.setLastAccessedTime(Instant.now());
        return String.valueOf(smth.isExpired());
    }
}
