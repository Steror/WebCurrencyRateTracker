package webcurrencyratetracker.services;

import webcurrencyratetracker.handlers.LTBFxRateHandler;
import webcurrencyratetracker.models.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import webcurrencyratetracker.repositories.FxRateRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class LTBFxRateService {

    private final FxRateRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(LTBFxRateService.class);
    private static final String URL_SOURCE = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu";

    @Autowired
    public LTBFxRateService(FxRateRepository repository) {
        this.repository = repository;
    }

    public void getCurrentFxRate() {
        try {
            URL urlObject = null;
            try {
                urlObject = new URL(URL_SOURCE); // Turn the string into a URL object
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // Open the stream (which returns an InputStream)
            assert urlObject != null;
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            URLConnection connection = new URL(URL_SOURCE).openConnection();
            // Required to avoid 403 Forbidden error
            connection.addRequestProperty("User-Agent", "PostmanRuntime/7.26.2");
            InputStream inStream = connection.getInputStream();

            // Create an XML reader
            XMLReader xr = XMLReaderFactory.createXMLReader();

            // Make XML reader use custom LTB handler
            LTBFxRateHandler handler = new LTBFxRateHandler();
            xr.setContentHandler(handler);

            // Wrap InputStream in InputSource for SAX parser
            InputSource inSource = new InputSource(inStream);

            // Parse it
            xr.parse(inSource);

            // Save to DB
            List<FxRate> fxRates = handler.getFxRates();
            repository.saveAll(fxRates);
        }
        catch(IOException | SAXException ioe) {
            ioe.printStackTrace();
        }
    }
}
