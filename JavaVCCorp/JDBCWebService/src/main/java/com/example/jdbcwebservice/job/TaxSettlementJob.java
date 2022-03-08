package com.example.jdbcwebservice.job;

import com.example.jdbcwebservice.dao.EmployeeDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaxSettlementJob implements Job {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] str = dateFormat.format(date).split("-");
        employeeDAO.getEmpTaxHibernate(str[1], str[0]);
        System.out.println("Done");
    }

//    public static void main(String[] args) throws SchedulerException {
//        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerName", "group1")
//                .withSchedule(CronScheduleBuilder.cronSchedule("59 59 23 L * ?")).build();
//        JobDetail job = JobBuilder.newJob(TaxSettlementJob.class).withIdentity("jobName", "group1").build();
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(job, trigger);
//    }
}
