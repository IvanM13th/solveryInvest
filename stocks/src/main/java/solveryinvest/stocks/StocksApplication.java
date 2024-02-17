package solveryinvest.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);
    }

}
