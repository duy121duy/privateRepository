package com.example.jdbcwebservice.job;

import com.example.jdbcwebservice.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyJob {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public MyJob(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Scheduled(cron = "*/40 * * * * ?")
    public void scheduleTaskUsingCronExpression() throws InterruptedException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] str = dateFormat.format(date).split("-");
        employeeDAO.getEmpTaxHibernate(str[1], str[0]);
        System.out.println("Done");
    }
}
