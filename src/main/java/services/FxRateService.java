package services;

import models.Currency;
import models.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.FxRateRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class FxRateService {
    private final FxRateRepository repository;

    @Autowired
    public FxRateService(FxRateRepository repository) {
        this.repository = repository;
    }

    // Find all entries for given source and target currency
    public List<FxRate> findFxRateHistory(final Currency sourceCurrency, final Currency targetCurrency) {
        return repository.findFxRatesBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency);
    }

    // Find all fx rates for specified date
    public List<FxRate> findFxRates(final LocalDate date) {
        return repository.findFxRatesByDate(date);
    }

    // Find fx rate for 2 currencies for current date
//    public FxRate findFxRateForCurrenciesToday(final Currency sourceCurrency, final Currency targetCurrency) {
//        return repository.findFxRateByDateAndSourceCurrencyAndTargetCurrency(LocalDate.now(), sourceCurrency, targetCurrency);
//    }
}
