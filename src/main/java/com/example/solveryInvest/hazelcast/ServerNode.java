package com.example.solveryInvest.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("hz")
public class ServerNode {

    @Bean
    public HazelcastInstance hazelcastServerNode() {
        Config config = new Config();
        config.getNetworkConfig().setPort(8887).setPortCount(1);
        config.setClusterName("test");
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
        var map = hzInstance.getMap("data");
        FlakeIdGenerator idGenerator = hzInstance.getFlakeIdGenerator("newid");
        for (int i = 0; i < 10; i++) {
            map.put(idGenerator.newId(), i);
        }
        return hzInstance;
    }
}
