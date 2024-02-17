package com.example.solveryInvest.hazelcast;


import com.example.solveryInvest.HazelClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.ExtendedMapEntry;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;


@Service("sessss")
@Slf4j
@RequiredArgsConstructor
public class Session {

    @Autowired
    private HazelcastIndexedSessionRepository sessionRepository;

    //private final SessionUpdateEntryProcessor sessionUpdateEntryProcessor;

    private final HazelClient client;

    private final HazelcastInstance hazelcastInstance;

    @PostConstruct
    public void getSess() {
        var map = hazelcastInstance.getMap("session_map");
        IMap<String, MapSession> sessionsImap = hazelcastInstance.getMap(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
        var user = "6667@555.ru";
        var sessions = sessionRepository.findByPrincipalName(user);
        System.out.println("qwe");
    }
}

