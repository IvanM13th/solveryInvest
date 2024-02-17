package solveryinvest.stocks.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;

@RequiredArgsConstructor
@Configuration
public class HazelcastRepository {
    private final HazelcastInstance hazelcastInstance;
    @Bean
    public HazelcastIndexedSessionRepository sessionRepository() {
        return new HazelcastIndexedSessionRepository(hazelcastInstance);
    }
}
