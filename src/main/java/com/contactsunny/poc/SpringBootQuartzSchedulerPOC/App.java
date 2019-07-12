package com.contactsunny.poc.SpringBootQuartzSchedulerPOC;

import com.contactsunny.poc.SpringBootQuartzSchedulerPOC.quartzJobs.SimpleQuartzJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        Change the date and time here when you're testing.
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = sdf.parse("12/07/2019 13:00:00");

        SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule();

        /*
        Change the repeat count and interval according to your needs during testing.
         */
        schedule.withIntervalInMinutes(1).withRepeatCount(2);

        JobDetail job = newJob(SimpleQuartzJob.class)
                // An identity for this particular job, something like an ID, within the provided group.
                .withIdentity("simpleJob", "simpleTriggerGroup")
                // Passing (or setting) data for the class which will be scheduled for the run.
                .usingJobData("stringVariable", "Hello World!")
                .usingJobData("intVariable", 123)
                // Building the job.
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                // An identity for this instance of the trigger, similar to the ID of a job.
                .withIdentity("simpleTrigger", "simpleTriggerGroup")
                // The date and time when the first instance of the job will be triggered.
                .startAt(date)
                // This is the ID we used to create the job earlier, for the provided group.
                .forJob("simpleJob", "simpleTriggerGroup")
                // The schedule we created earlier, with the repeat count and interval.
                .withSchedule(schedule)
                // Building the trigger.
                .build();

        /*
        Getting the scheduler which will actually schedule the job for later.
         */
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // Scheduling the job.
        scheduler.scheduleJob(job, trigger);

        // Starting the scheduler. Until this is done, nothing will be scheduled and no jobs will be run.
        scheduler.start();
    }
}
