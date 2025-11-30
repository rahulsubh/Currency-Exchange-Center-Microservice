package com.rahul.Controller;

import com.rahul.Repository.CurrencyExchangeRepo;
import com.rahul.bean.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeRepo currencyExchangeRepo;

    @Value("${server.port}")
    private String port;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to
    ){
        CurrencyExchange currencyExchange = currencyExchangeRepo.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
