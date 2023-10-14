package com.example.solveryInvest.hazelcast;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.stereotype.Service;



@Service("sessss")
@Slf4j
public class Session {

    @Autowired
    private  HazelcastIndexedSessionRepository sessionRepository;

    @PostConstruct
    public void getSess() {
        var user = "6667@555.ru";
        if (sessionRepository.findByPrincipalName(user).size() > 0) {
            var q = sessionRepository.findByPrincipalName(user);
            log.info("Session for user {} has been found", user);
        } else {
            log.info("Session for user {} has NOT been found", user);
        }
    }
}
