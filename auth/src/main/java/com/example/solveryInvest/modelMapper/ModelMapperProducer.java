package com.example.solveryInvest.modelMapper;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("model_mapper_producer")
@RequiredArgsConstructor
public class ModelMapperProducer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.typeMap(UserDto.class, User.class);

        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.skip(UserDto::setPassword));
        return modelMapper;
    }
}
