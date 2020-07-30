package webcurrencyratetracker.services;

import webcurrencyratetracker.models.CURRENCY;
import webcurrencyratetracker.models.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcurrencyratetracker.repositories.FxRateRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class FxRateService {
    private final FxRateRepository repository;

    @Autowired
    public FxRateService(FxRateRepository repository) {
        this.repository = repository;
    }

    // Find all entries for EUR and target currency
    public List<FxRate> findFxRatesForCurrency(final CURRENCY targetCurrency) {
        return repository.findFxRatesByTargetCurrency(targetCurrency);
    }

    // Find all fx rates for current date
    public List<FxRate> findFxRates() {
        return repository.findFxRatesByDate(LocalDate.now());
    }

    // Save a list of FxRates
    public void saveFxRates(final List<FxRate> fxRates) {
        fxRates.removeIf(fxRate ->
                repository.existsBySourceCurrencyAndTargetCurrencyAndDate(
                        fxRate.getSourceCurrency(), fxRate.getTargetCurrency(), fxRate.getDate()));
        repository.saveAll(fxRates);
    }
}
