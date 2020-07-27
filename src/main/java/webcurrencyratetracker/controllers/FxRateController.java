package webcurrencyratetracker.controllers;

import webcurrencyratetracker.models.Currency;
import webcurrencyratetracker.models.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webcurrencyratetracker.services.FxRateService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/FxRate")
public class FxRateController {

    private final FxRateService service;

    @Autowired
    public FxRateController(FxRateService service) {
        this.service = service;
    }

    @GetMapping(path="/FxRatesForCurrency")
    public List<FxRate> getFxRatesForCurrency(
            @RequestBody Currency targetCurrency) {
        return service.findFxRatesForCurrency(targetCurrency);
    }

    @GetMapping(path="/FxRates")
    public List<FxRate> getFxRates(
            @RequestBody LocalDate date) {
        return service.findFxRates(date);
    }
}
