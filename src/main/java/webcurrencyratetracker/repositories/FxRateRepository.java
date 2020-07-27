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

    // TODO: Sort by date and add new method to filter by date
    // This query is used to get all exchange rate history between EUR and specified currency (source is always EUR)
    @Query("SELECT f FROM FxRate f WHERE f.targetCurrency = :targetCurrency ")
    List<FxRate> findFxRatesByTargetCurrency(
            @Param("targetCurrency") Currency targetCurrency);

    // TODO: NEEDS TESTING
    // This query returns exchange rates equal to or before the specified date for all currencies
    @Query(value = "SELECT DISTINCT ON (TARGET_CURRENCY) SOURCE_CURRENCY, TARGET_CURRENCY, EXCHANGE_RATE, DATE FROM FX_RATE WHERE DATE <= :specifiedDate ORDER BY DATE DESC;",
            nativeQuery = true)
    List<FxRate> findFxRatesByDate(
            @Param("specifiedDate") LocalDate specifiedDate);

    boolean existsBySourceCurrencyAndTargetCurrencyAndDate(Currency sourceCurrency, Currency targetCurrency, LocalDate date);
}
