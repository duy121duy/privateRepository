package com.example.jdbcwebservice.services.impl;

import com.example.jdbcwebservice.dao.EmployeeDAO;
import com.example.jdbcwebservice.entities.DeptEmp;
import com.example.jdbcwebservice.entities.Employee;
import com.example.jdbcwebservice.entities.Salary;
import com.example.jdbcwebservice.entities.Title;
import com.example.jdbcwebservice.entities.dto.CaculatedSalary;
import com.example.jdbcwebservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getEmployees(Date hireDateFrom, int salary, String deptNo, String title) {
        return employeeDAO.getEmployees(hireDateFrom, salary, deptNo, title);
    }

    @Override
    public Employee addEmployeeHibernate(Employee employee, Title title, Salary salary) {
        return employeeDAO.addEmployeeHibernate(employee, title, salary);
    }

    @Override
    public Employee addEmployeeJDBC(Employee employee, Title title, Salary salary) {
        return employeeDAO.addEmployeeJDBC(employee, title, salary);
    }

    @Override
    public DeptEmp changeDeptHibernate(int empNo, String deptNo) {
        return employeeDAO.changeDeptHibernate(empNo, deptNo);
    }

    @Override
    public DeptEmp changeDeptJDBC(int empNo, String deptNo) {
        return employeeDAO.changeDeptJDBC(empNo, deptNo);
    }

    @Override
    public CaculatedSalary getSalaryHibernate(Date fromDate) {
        return employeeDAO.getSalaryHibernate(fromDate);
    }

    @Override
    public CaculatedSalary getSalaryJDBC(Date fromDate) {
        return employeeDAO.getSalaryJDBC(fromDate);
    }
}
