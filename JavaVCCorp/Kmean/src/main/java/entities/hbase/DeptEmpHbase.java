package entities.hbase;

import java.util.Objects;

public class DeptEmpHbase {
    private int empNo;

    // Title, deptNo
    private String deptNo;

    private String duration;

    public DeptEmpHbase() {
    }

    public DeptEmpHbase(int empNo, String deptNo, String duration) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "DeptEmpHbase{" +
                "empNo=" + empNo +
                ", deptNo='" + deptNo + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEmpHbase that = (DeptEmpHbase) o;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
