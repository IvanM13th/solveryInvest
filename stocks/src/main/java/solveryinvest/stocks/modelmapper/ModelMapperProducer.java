package solveryinvest.stocks.modelmapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.entity.Portfolio;
import solveryinvest.stocks.entity.PortfolioAsset;

@Configuration("model_mapper_producer")
@RequiredArgsConstructor
public class ModelMapperProducer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.typeMap(Balance.class, BalanceDto.class);
        modelMapper.typeMap(Portfolio.class, PortfolioDto.class);
        modelMapper.typeMap(Asset.class, AssetDto.class);
        modelMapper.typeMap(PortfolioAsset.class, AssetDto.class)
                .addMapping(pa -> pa.getAsset().getFigi(), AssetDto::setFigi)
                .addMapping(pa -> pa.getAsset().getName(), AssetDto::setName)
                .addMapping(pa -> pa.getAsset().getTicker(), AssetDto::setTicker);

        modelMapper.typeMap(PortfolioDto.class, Portfolio.class);
        return modelMapper;
    }
}
