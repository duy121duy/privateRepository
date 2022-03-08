package com.example.jdbcwebservice.entities.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tax {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "tax")
    private int tax;

    public Tax() {
    }

    public Tax(int empNo, int tax) {
        this.empNo = empNo;
        this.tax = tax;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}
