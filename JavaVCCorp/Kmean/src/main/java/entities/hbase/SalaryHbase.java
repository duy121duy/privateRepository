package entities.hbase;

import java.util.Objects;

public class SalaryHbase {
    private int empNo;

    // Title, deptNo
    private long salary;

    private String duration;

    public SalaryHbase(int empNo, long salary, String duration) {
        this.empNo = empNo;
        this.salary = salary;
        this.duration = duration;
    }

    public SalaryHbase() {
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SalaryHbase{" +
                "empNo=" + empNo +
                ", salary=" + salary +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryHbase that = (SalaryHbase) o;
        return empNo == that.empNo && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, duration);
    }
}
