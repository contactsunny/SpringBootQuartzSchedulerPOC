package com.contactsunny.poc.SpringBootQuartzSchedulerPOC.quartzJobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleQuartzJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(SimpleQuartzJob.class);

    private String stringVariable;
    private int intVariable;

    public String getStringVariable() {
        return stringVariable;
    }

    public void setStringVariable(String stringVariable) {
        this.stringVariable = stringVariable;
    }

    public int getIntVariable() {
        return intVariable;
    }

    public void setIntVariable(int intVariable) {
        this.intVariable = intVariable;
    }

    /**
     * This is the function which will be called when the scheduled job will be run.
     * 
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        this.setStringVariable(dataMap.getString("stringVariable"));
        this.setIntVariable(dataMap.getInt("intVariable"));

        logger.info("Executing a job of type: " + this.getClass().getName());
        logger.info("stringVariable: " + stringVariable);
        logger.info("intVariable: " + intVariable);
    }
}
