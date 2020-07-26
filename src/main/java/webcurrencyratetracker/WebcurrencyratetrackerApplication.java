package webcurrencyratetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import webcurrencyratetracker.models.Currency;
import webcurrencyratetracker.models.FxRate;
import webcurrencyratetracker.repositories.FxRateRepository;
import webcurrencyratetracker.services.LTBFxRateService;

import java.time.LocalDate;

@SpringBootApplication
public class WebcurrencyratetrackerApplication implements CommandLineRunner {

	@Autowired
	private LTBFxRateService ltbFxRateService;
	@Autowired
	private FxRateRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(WebcurrencyratetrackerApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		repository.save(new FxRate(null, Currency.EUR, Currency.USD, 1.16d, LocalDate.parse("2020-07-25")));
		ltbFxRateService.getCurrentFxRates();
		ltbFxRateService.getFxRatesForCurrency(
				Currency.valueOf("USD"), LocalDate.parse("2020-06-01"), LocalDate.parse("2020-08-01"));
		ltbFxRateService.getFxRatesForCurrency(
				Currency.valueOf("GBP"), LocalDate.parse("2020-06-01"), LocalDate.parse("2020-08-01"));
		ltbFxRateService.getFxRatesForCurrency(
				Currency.valueOf("CAD"), LocalDate.parse("2020-06-01"), LocalDate.parse("2020-08-01"));
	}
}
