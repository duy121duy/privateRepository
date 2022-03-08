package com.example.jdbcwebservice.controller;

import com.example.jdbcwebservice.entities.DeptEmp;
import com.example.jdbcwebservice.entities.Employee;
import com.example.jdbcwebservice.entities.Salary;
import com.example.jdbcwebservice.entities.Title;
import com.example.jdbcwebservice.entities.dto.CaculatedSalary;
import com.example.jdbcwebservice.entities.dto.NewEmployee;
import com.example.jdbcwebservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

//    @Autowired
//    private QuartzConfig quartzConfig;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(name = "hireDateFrom") String hireDateFrom,
                                                       @RequestParam(name = "salary") int salary,
                                                       @RequestParam(name = "dept_no") String deptNo,
                                                       @RequestParam(name = "title") String title) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new ResponseEntity<>(employeeService.getEmployees(dateFormat.parse(hireDateFrom), salary, deptNo, title), HttpStatus.OK);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody NewEmployee newEmployee) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Employee employee = null;
        Date currentDate = new Date();
        try {
            employee = new Employee(0, dateFormat.parse(newEmployee.getBirthDate()), newEmployee.getFirstName(), newEmployee.getLastName(),
                    newEmployee.getGender(), dateFormat.parse("9999-01-01"));
            Salary salary = new Salary(0, newEmployee.getSalary(), currentDate, dateFormat.parse("9999-01-01"));
            Title title = new Title(0, newEmployee.getTitle(), currentDate, dateFormat.parse("9999-01-01"));

            Employee result = employeeService.addEmployeeHibernate(employee, title, salary);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getSalaries")
    public ResponseEntity<CaculatedSalary> getSalaries(@RequestParam("from_date") String fromDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            CaculatedSalary caculatedSalary = employeeService.getSalaryHibernate(dateFormat.parse(fromDate));
            return new ResponseEntity<>(caculatedSalary, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeDept")
    public ResponseEntity<DeptEmp> changeDept(@RequestParam("emp_no") int empNo,
                                              @RequestParam("dept_no") String deptNo) {
        try {
            DeptEmp deptEmp = employeeService.changeDeptHibernate(empNo, deptNo);
            return new ResponseEntity<>(deptEmp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
