package com.example.jdbcwebservice.services;

import com.example.jdbcwebservice.entities.DeptEmp;
import com.example.jdbcwebservice.entities.Employee;
import com.example.jdbcwebservice.entities.Salary;
import com.example.jdbcwebservice.entities.Title;
import com.example.jdbcwebservice.entities.dto.CaculatedSalary;
import com.example.jdbcwebservice.entities.dto.Tax;

import java.util.Date;
import java.util.List;

public interface EmployeeService {
    public List<Employee> getEmployees(Date hireDateFrom, int salary, String deptNo, String title);

    public Employee addEmployeeHibernate(Employee employee, Title title, Salary salary);

    public Employee addEmployeeJDBC(Employee employee, Title title, Salary salary);

    public DeptEmp changeDeptHibernate(int empNo, String deptNo);

    public DeptEmp changeDeptJDBC(int empNo, String deptNo);

    public CaculatedSalary getSalaryHibernate(Date fromDate);

    public CaculatedSalary getSalaryJDBC(Date fromDate);
}
