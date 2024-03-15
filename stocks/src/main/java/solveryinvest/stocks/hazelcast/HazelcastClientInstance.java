package solveryinvest.stocks.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.YamlClientConfigBuilder;
import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import solveryinvest.stocks.entity.User;

import java.io.IOException;
import java.util.Objects;

@Configuration
@Slf4j
public class HazelcastClientInstance {

    @Bean
    @SpringSessionHazelcastInstance
    public HazelcastInstance hazelcastInstance() {
        HazelcastInstance client = null;
        var url = getClass().getClassLoader().getResource("hazelcast.client.config.yaml");
        if (Objects.nonNull(url)) {
            try {
                var config = new YamlClientConfigBuilder(url).build();
                config.setClassLoader(HazelcastClientInstance.class.getClassLoader());
                client = HazelcastClient.newHazelcastClient(config);
                SerializerConfig serializerConfig = new SerializerConfig();
                serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
                config.getSerializationConfig().addSerializerConfig(serializerConfig);
                config.getUserCodeDeploymentConfig().setEnabled(false)
                        .addClass(MapSession.class)
                        .addClass(SessionUpdateEntryProcessor.class);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        return client;
    }
}
