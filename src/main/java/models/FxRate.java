package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class FxRate {
    @Id
    @GeneratedValue
    private long id;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private double exchangeRate;
    private LocalDate date;
}
