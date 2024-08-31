package com.obss_final_project.e_commerce.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(MyJobExecutor.class)
                .withIdentity("myJob", "myJobGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myJobTrigger1(JobDetail myJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail)
                .withIdentity("myTrigger1", "myTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .usingJobData("jobID", "Job-1")
                .build();
    }

//    @Bean
//    public Trigger myJobTrigger2(JobDetail myJobDetail) {
//        return TriggerBuilder.newTrigger()
//                .forJob(myJobDetail)
//                .withIdentity("myTrigger2", "myTriggerGroup")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
//                .usingJobData("jobID", "Job-2")
//                .build();
//    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory, JobDetail myJobDetail,
                               Trigger myJobTrigger1, Trigger myJobTrigger2) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(myJobDetail, myJobTrigger1);
//        scheduler.scheduleJob(myJobDetail, myJobTrigger2);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }
}
