package webcurrencyratetracker.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import webcurrencyratetracker.config.AutoWiringSpringBeanJobFactory;
import webcurrencyratetracker.services.LTBFxRateService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class QuartzScheduler {

    private static final Logger logger = LoggerFactory.getLogger(QuartzScheduler.class);

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Quartz Scheduler created...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, @Qualifier("schedulerFactoryBean") SchedulerFactoryBean factory) throws SchedulerException {
        logger.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);

        logger.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobDetail jobDetail() {
        return newJob().ofType(LTBFxRateService.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
                .withDescription("Invoke Currency Update Job...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        logger.info("Configuring trigger to fire every day at 12:00 mid-day");

        return newTrigger()
                .withIdentity(TriggerKey.triggerKey("Qrtz_Trigger"))
                .withDescription("Trigger for getting daily currency fx rate updates")
                .withSchedule(dailyAtHourAndMinute(12, 0))
                .forJob(job)
                .build();
    }
}
