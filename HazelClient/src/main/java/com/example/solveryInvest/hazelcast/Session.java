package com.example.solveryInvest.hazelcast;


import com.example.solveryInvest.HazelClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.ExtendedMapEntry;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
       /* IMap<String, MapSession> sessions = hazelcastInstance.getMap(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
        //var sess = sessionRepository.createSession();
        //sessionRepository.save(sess);
        var user = "6667@555.ru";
        if (sessionRepository.findByPrincipalName(user).size() > 0) {
            var map = sessionRepository.findByPrincipalName(user);
            var keys = map.keySet();
            for (var k : keys) {
*//*                for (var entry : sessions.entrySet()) {
                    if (entry.getKey().equals(k)) {
                        sessionUpdateEntryProcessor.process(entry);
                    }
                }*//*
                var qwe = sessions.executeOnKey(k, new SessionUpdateEntryProcessor());
                log.info(k);
                log.info("found");
            }

            log.info(map.toString());
*//*            for (var sess : map.entrySet()) {
                var id = sess.getKey();
                //sessionRepository.save(sess);
                log.info("success");
            }*//*
        } else {
            log.info("Session for user {} has NOT been found", user);
        }*/
    }
}
