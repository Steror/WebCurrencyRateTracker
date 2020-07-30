package webcurrencyratetracker.handlers;

import webcurrencyratetracker.models.CURRENCY;
import webcurrencyratetracker.models.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LTBFxRateHandler extends DefaultHandler {

    private static final Logger logger = LoggerFactory.getLogger(LTBFxRateHandler.class);
    private boolean isCurrency, isExchangeRate, isDate;
    private FxRate currentFxRate;
    private final List<FxRate> fxRates = new ArrayList<>();

    public List<FxRate> getFxRates() {
        return fxRates;
    }

    public LTBFxRateHandler() {
        super();
    }

    @Override
    public void startDocument() {
        logger.info("Start document");
    }

    @Override
    public void endDocument() {
        logger.info("End document");
    }

    @Override
    public void startElement(String uri, String name, String qName, Attributes atts)
    {
        if (qName.equals("FxRate")) {
            currentFxRate = new FxRate();
            logger.trace("New FxRate");
        }
        if (qName.equals("Dt")) { isDate = true; }
        if (qName.equals("Ccy")) { isCurrency = true; }
        if (qName.equals("Amt")) { isExchangeRate = true; }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isDate) {
            currentFxRate.setDate(LocalDate.parse(new String(ch, start, length)));
            logger.trace("Date: " + currentFxRate.getDate());
        }
        if (isCurrency) {
            if (currentFxRate.getSourceCurrency() == null) {
                currentFxRate.setSourceCurrency(CURRENCY.valueOf(new String(ch, start, length)));
                logger.trace("Source currency: " + currentFxRate.getSourceCurrency());
            }
            else {
                currentFxRate.setTargetCurrency(CURRENCY.valueOf(new String(ch, start, length)));
                logger.trace("Target currency: " + currentFxRate.getTargetCurrency());
            }
        }
        if (isExchangeRate) {
            if (currentFxRate.getTargetCurrency() != null) {
                currentFxRate.setExchangeRate(Double.parseDouble(new String(ch, start, length)));
                logger.trace("Exchange rate: " + currentFxRate.getExchangeRate());
            }
        }
    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if(qName.equals("FxRate")) {
            fxRates.add(currentFxRate);
            currentFxRate = null;
        }
        if(qName.equals("Dt")) { isDate = false; }
        if(qName.equals("Ccy")) { isCurrency = false; }
        if(qName.equals("Amt")) { isExchangeRate = false; }
    }
}
