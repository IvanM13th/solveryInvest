package com.example.solveryInvest;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.session.*;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.stereotype.Service;


@Service("hz-client")
@SpringSessionHazelcastInstance
@RequiredArgsConstructor
@Slf4j
public class HazelClient {

    @PostConstruct
    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress("localhost:8888");
        config.setClusterName("spring-session-cluster");
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);
        config.getUserCodeDeploymentConfig().setEnabled(true).addClass(Session.class)
                .addClass(MapSession.class).addClass(PrincipalNameExtractor.class.getName());

        return HazelcastClient.newHazelcastClient(config);
    }
}
