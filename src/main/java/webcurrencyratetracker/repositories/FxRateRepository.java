package webcurrencyratetracker.repositories;

import webcurrencyratetracker.models.Currency;
import webcurrencyratetracker.models.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FxRateRepository extends JpaRepository<FxRate, Long> {

    // This query is used to get all exchange rate history between EUR and specified currency
    // TODO: Sort by date and add new method to filter by date
    @Query("SELECT f FROM FxRate f WHERE f.targetCurrency = :targetCurrency ")
    List<FxRate> findFxRatesByTargetCurrency(
            @Param("targetCurrency") Currency targetCurrency);

    @Query("SELECT f FROM FxRate f WHERE f.date = :date ")
    List<FxRate> findFxRatesByDate(
            @Param("date") LocalDate date);

//    @Query("SELECT f FROM FxRate f WHERE f.date = :date AND f.sourceCurrency = :sourceCurrency AND f.targetCurrency = :targetCurrency")
//    FxRate findFxRateByDateAndSourceCurrencyAndTargetCurrency(
//            @Param("date") LocalDate date,
//            @Param("sourceCurrency") Currency sourceCurrency,
//            @Param("targetCurrency") Currency targetCurrency);
}
