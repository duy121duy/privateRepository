package com.example.jdbcwebservice.entities.dto;

import java.util.List;

public class CaculatedSalary {
    private List<EmpSalary> employeesSalaries;

    private List<DeptSalary> departmentSalaries;

    private Long companySalaries;

    public CaculatedSalary() {
    }

    public CaculatedSalary(List<EmpSalary> employeesSalaries, List<DeptSalary> departmentSalaries, Long companySalaries) {
        this.employeesSalaries = employeesSalaries;
        this.departmentSalaries = departmentSalaries;
        this.companySalaries = companySalaries;
    }

    public List<EmpSalary> getEmployeesSalaries() {
        return employeesSalaries;
    }

    public void setEmployeesSalaries(List<EmpSalary> employeesSalaries) {
        this.employeesSalaries = employeesSalaries;
    }

    public List<DeptSalary> getDepartmentSalaries() {
        return departmentSalaries;
    }

    public void setDepartmentSalaries(List<DeptSalary> departmentSalaries) {
        this.departmentSalaries = departmentSalaries;
    }

    public Long getCompanySalaries() {
        return companySalaries;
    }

    public void setCompanySalaries(Long companySalaries) {
        this.companySalaries = companySalaries;
    }
}
