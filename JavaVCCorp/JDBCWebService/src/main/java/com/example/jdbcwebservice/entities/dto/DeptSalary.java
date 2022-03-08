package com.example.jdbcwebservice.entities.dto;

public class DeptSalary {
    private String deptNo;
    private Long totalSalary;

    public DeptSalary() {
    }

    public DeptSalary(String deptNo, Long totalSalary) {
        this.deptNo = deptNo;
        this.totalSalary = totalSalary;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Long getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
    }

    @Override
    public String toString() {
        return "DeptSalary{" +
                "deptNo='" + deptNo + '\'' +
                ", totalSalary=" + totalSalary +
                '}';
    }
}
