package webcurrencyratetracker.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import webcurrencyratetracker.handlers.LTBFxRateHandler;
import webcurrencyratetracker.models.Currency;
import webcurrencyratetracker.models.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;

@Service
public class LTBFxRateService implements Job {

    @Autowired
    private FxRateService service;

    private static final Logger logger = LoggerFactory.getLogger(LTBFxRateService.class);
    private static final String URL_CURRENT_FX_RATES = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu";
    private static final String URL_FX_RATES_FOR_CURRENCY = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=eu";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        logger.info("The currency update job has begun...");
        getCurrentFxRates();

        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }

    public void getCurrentFxRates() {
        try {
            URLConnection connection = new URL(URL_CURRENT_FX_RATES).openConnection(); // Start connection to URL
            parseFxRates(connection);
        }
        catch(IOException | SAXException ioe) {
            ioe.printStackTrace();
        }
    }

    public void getFxRatesForCurrency(final Currency currency, final LocalDate dateFrom, final LocalDate dateTo) {
        try {
            // Build URL
            // Example: http://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=eu&ccy=usd&dtFrom=2020-06-25&dtTo=2020-07-28
            URLConnection connection = new URL(URL_FX_RATES_FOR_CURRENCY + "&ccy=" + currency + "&dtFrom=" + dateFrom + "&dtTo=" + dateTo).openConnection(); // Start connection to URL
            parseFxRates(connection);
        }
        catch(IOException | SAXException ioe) {
            ioe.printStackTrace();
        }
    }

    // Gets data from URL Connection, parses it and saves to DB
    private void parseFxRates(URLConnection connection) throws SAXException, IOException {
        connection.addRequestProperty("User-Agent", "PostmanRuntime/7.26.2"); // Add User-Agent header, required to avoid 403 Forbidden error
        InputStream inStream = connection.getInputStream();
        XMLReader xr = XMLReaderFactory.createXMLReader();
        LTBFxRateHandler handler = new LTBFxRateHandler();
        xr.setContentHandler(handler);                     // Make XML reader use custom LTB handler
        InputSource inSource = new InputSource(inStream); // Wrap InputStream in InputSource for SAX parser
        xr.parse(inSource);                               // Parse it

        List<FxRate> fxRates = handler.getFxRates(); // Get parsed fxRates from handler
        service.saveFxRates(fxRates); // Save to DB
    }
}
