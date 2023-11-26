package com.example.solveryInvest.hazelcast;

import lombok.RequiredArgsConstructor;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.stereotype.Service;

@Service("session")
@RequiredArgsConstructor
public class SessionLogger {

    HazelcastIndexedSessionRepository sessionRepository;

}
