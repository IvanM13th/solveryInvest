package solveryinvest.stocks.modelMapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.entity.Balance;


@Configuration("model_mapper_producer")
@RequiredArgsConstructor
public class ModelMapperProducer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.typeMap(Balance.class, BalanceDto.class);
        return modelMapper;
    }
}
