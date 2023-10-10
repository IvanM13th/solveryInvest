package com.example.solveryInvest.hazelcast;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.stereotype.Service;



@Service("sessss")
@RequiredArgsConstructor
public class Session {
    private final HazelcastIndexedSessionRepository sessionRepository;

    @PostConstruct
    public void getSess() {
        var session = sessionRepository.findByPrincipalName("6667@555.ru");
        var map2 = sessionRepository.findByPrincipalName("6667@555.ruweqwe");
        System.out.println(session);
    }
}
