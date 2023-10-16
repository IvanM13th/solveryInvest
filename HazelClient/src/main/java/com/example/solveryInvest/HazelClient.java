package com.example.solveryInvest;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.YamlClientConfigBuilder;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service("hz-client")
@Slf4j
public class HazelClient {

    @PostConstruct
    @Bean
    @SpringSessionHazelcastInstance
    public HazelcastInstance hazelcastInstance() {
        ClientConfig config = new ClientConfig();
        var url = getClass().getClassLoader().getResource("hazelcast.client.config.yaml");
        if (Objects.nonNull(url)) {
            try {
                config = new YamlClientConfigBuilder(url).build();
                SerializerConfig serializerConfig = new SerializerConfig();
                serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
                config.getSerializationConfig().addSerializerConfig(serializerConfig);
                config.getUserCodeDeploymentConfig().setEnabled(true)
                        .addClass(Session.class)
                        .addClass(MapSession.class)
                        .addClass(PrincipalNameExtractor.class.getName())
                        .addClass(SessionUpdateEntryProcessor.class);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        return HazelcastClient.newHazelcastClient(config);
    }
}
