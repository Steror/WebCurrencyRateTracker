package webcurrencyratetracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CurrencyUpdateJobService {

    public static final long EXECUTION_TIME = 5000L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger count = new AtomicInteger();

    public void executeCurrencyUpdateJob() {

        logger.info("The currency update job has begun...");
        try {
            Thread.sleep(EXECUTION_TIME);
        } catch (InterruptedException e) {
            logger.error("Error while executing currency update job", e);
        } finally {
            count.incrementAndGet();
            logger.info("Currency update job has finished...");
        }
    }

    public int getNumberOfInvocations() {
        return count.get();
    }
}
