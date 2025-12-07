package com.rahul.Controller;

import com.rahul.bean.CurrencyConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        Map<String, String> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        ResponseEntity<CurrencyConversion> entity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, map);
        CurrencyConversion currencyConversion = entity.getBody();
        return new CurrencyConversion
                (currencyConversion.getId(), from,
                        to, quantity, currencyConversion.getConversionMultiple(),
                        quantity.multiply(currencyConversion.getConversionMultiple()),
                        currencyConversion.getEnvironment());
    }
}
