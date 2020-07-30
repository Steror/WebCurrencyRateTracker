package webcurrencyratetracker.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import webcurrencyratetracker.models.CURRENCY;
import webcurrencyratetracker.models.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webcurrencyratetracker.services.FxRateService;

import java.util.List;

@RestController
@RequestMapping("/FxRate")
@CrossOrigin(origins = "http://localhost:4200")
public class FxRateController {

    private final FxRateService service;

    @Autowired
    public FxRateController(FxRateService service) {
        this.service = service;
    }

    @GetMapping(path="/FxRatesForCurrency")
    public List<FxRate> getFxRatesForCurrency(@RequestParam("currency")final CURRENCY targetCurrency) {
        return service.findFxRatesForCurrency(targetCurrency);
    }

    @GetMapping(path="/CurrentFxRates")
    public List<FxRate> getCurrentFxRates() {
        return service.findFxRates();
    }
}
