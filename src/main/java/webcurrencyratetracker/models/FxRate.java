package webcurrencyratetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FxRateId.class)
public class FxRate {
    @Id
    @Enumerated(EnumType.STRING)    // Strings for DB readability while manually testing
    private CURRENCY sourceCurrency;
    @Id
    @Enumerated(EnumType.STRING)
    private CURRENCY targetCurrency;
    private Double exchangeRate;
    @Id
    private LocalDate date;
}
