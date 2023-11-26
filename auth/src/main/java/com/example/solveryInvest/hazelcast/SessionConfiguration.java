package com.example.solveryInvest.hazelcast;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.MapSession;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

@Configuration
@Slf4j
public class SessionConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = null;
        try {
            var url = getClass().getClassLoader().getResource("hazelcast.yaml");
            if (Objects.nonNull(url)) {
                config = new YamlConfigBuilder(url).build();
                AttributeConfig attributeConfig = new AttributeConfig()
                        .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                        .setExtractorClassName(PrincipalNameExtractor.class.getName());

                config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                        .addAttributeConfig(attributeConfig)
                        .addIndexConfig(new IndexConfig(IndexType.HASH, HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

                SerializerConfig serializerConfig = new SerializerConfig();
                serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
                config.getSerializationConfig().addSerializerConfig(serializerConfig);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public SessionRepositoryCustomizer<HazelcastIndexedSessionRepository> customize() {
        return (sessionRepository -> {
            sessionRepository.setFlushMode(FlushMode.IMMEDIATE);
            sessionRepository.setSaveMode(SaveMode.ALWAYS);
            sessionRepository.setSessionMapName(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
            sessionRepository.setDefaultMaxInactiveInterval(Duration.ofSeconds(120));
        });
    }
}
