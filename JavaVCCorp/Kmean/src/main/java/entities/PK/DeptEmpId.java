package entities.PK;

import java.io.Serializable;
import java.util.Objects;

public class DeptEmpId implements Serializable {
    private int empNo;

    private String deptNo;

    public DeptEmpId() {
    }

    public DeptEmpId(int empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEmpId deptEmpId = (DeptEmpId) o;
        return empNo == deptEmpId.empNo && Objects.equals(deptNo, deptEmpId.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
