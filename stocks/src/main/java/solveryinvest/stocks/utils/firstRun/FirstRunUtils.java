package solveryinvest.stocks.utils.firstRun;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.service.AssetService;

@Component("first-run-utils")
@RequiredArgsConstructor
public class FirstRunUtils implements CommandLineRunner {

    private final AssetService assetService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        assetService.getAssetsWhenAppStarts();
    }
}
