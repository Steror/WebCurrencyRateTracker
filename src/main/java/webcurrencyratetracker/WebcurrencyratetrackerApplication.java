package webcurrencyratetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import webcurrencyratetracker.models.Currency;
import webcurrencyratetracker.services.LTBFxRateService;

import java.time.LocalDate;

@SpringBootApplication
public class WebcurrencyratetrackerApplication implements CommandLineRunner {

	@Autowired
	private LTBFxRateService ltbFxRateService;

	public static void main(String[] args) {
		SpringApplication.run(WebcurrencyratetrackerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		ltbFxRateService.getCurrentFxRates();
		ltbFxRateService.getFxRatesForCurrency(
				Currency.USD, LocalDate.now().minusMonths(2), LocalDate.now());
		ltbFxRateService.getFxRatesForCurrency(
				Currency.GBP, LocalDate.now().minusMonths(2), LocalDate.now());
		ltbFxRateService.getFxRatesForCurrency(
				Currency.CAD, LocalDate.now().minusMonths(2), LocalDate.now());
	}
}
