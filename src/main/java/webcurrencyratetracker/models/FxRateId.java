package webcurrencyratetracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FxRateId implements Serializable {
    private CURRENCY sourceCurrency;
    private CURRENCY targetCurrency;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FxRateId fxRateId = (FxRateId) o;
        return sourceCurrency == fxRateId.sourceCurrency &&
                targetCurrency == fxRateId.targetCurrency &&
                date.equals(fxRateId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCurrency, targetCurrency, date);
    }
}
