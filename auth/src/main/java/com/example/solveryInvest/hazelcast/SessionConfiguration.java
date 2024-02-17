package com.example.solveryInvest.hazelcast;

import com.example.solveryInvest.dto.HazelcastUserData;
import com.example.solveryInvest.entity.User;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.MapSession;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@Configuration
@Slf4j
@EnableHazelcastHttpSession
public class SessionConfiguration {

    @Value("${hazelcast.inactive.interval}")
    private Integer inactiveInterval;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        HazelcastInstance hzInstance = null;
        try {
            var url = getClass().getClassLoader().getResource("hazelcast.yaml");
            if (Objects.nonNull(url)) {
                var config = new YamlConfigBuilder(url).build();
                config.setClassLoader(User.class.getClassLoader());
                hzInstance = Hazelcast.newHazelcastInstance(config);
                AttributeConfig attributeConfig = new AttributeConfig()
                        .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                        .setExtractorClassName(PrincipalNameExtractor.class.getName());

                config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                        .addAttributeConfig(attributeConfig)
                        .addIndexConfig(new IndexConfig(IndexType.HASH, HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

                SerializerConfig serializerConfig = new SerializerConfig();
                serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
                config.getSerializationConfig().addSerializerConfig(serializerConfig);
                var depl = config.getUserCodeDeploymentConfig();
                depl.setEnabled(true)
                        .setClassCacheMode(UserCodeDeploymentConfig.ClassCacheMode.ETERNAL)
                        .setProviderMode(UserCodeDeploymentConfig.ProviderMode.LOCAL_AND_CACHED_CLASSES);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return hzInstance;
    }

    @Bean
    public SessionRepositoryCustomizer<HazelcastIndexedSessionRepository> customize() {
        return (sessionRepository -> {
            sessionRepository.setFlushMode(FlushMode.IMMEDIATE);
            sessionRepository.setSaveMode(SaveMode.ALWAYS);
            sessionRepository.setSessionMapName(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
            sessionRepository.setDefaultMaxInactiveInterval(Duration.ofSeconds(inactiveInterval));
        });
    }
}
