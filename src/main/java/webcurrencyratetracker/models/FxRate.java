package webcurrencyratetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FxRateId.class)
public class FxRate {
//    @Id
//    @GeneratedValue
//    private Long id;
    @Id
    private Currency sourceCurrency;
    @Id
    private Currency targetCurrency;
    private Double exchangeRate;
    @Id
    private LocalDate date;
}
