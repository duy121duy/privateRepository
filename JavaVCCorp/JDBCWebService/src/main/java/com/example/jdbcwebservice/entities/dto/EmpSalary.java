package com.example.jdbcwebservice.entities.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class EmpSalary {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "total_salary")
    private long totalSalary;

    public EmpSalary() {
    }

    public EmpSalary(int empNo, String deptNo, long totalSalary) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.totalSalary = totalSalary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public long getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(long totalSalary) {
        this.totalSalary = totalSalary;
    }
}
