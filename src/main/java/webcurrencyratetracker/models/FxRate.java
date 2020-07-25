package webcurrencyratetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FxRate {
    @Id
    @GeneratedValue
    private Long id;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private Double exchangeRate;
    private LocalDate date;
}
