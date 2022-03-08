package entities.PK;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SalaryId implements Serializable {
    private int empNo;

    private Date fromDate;

    public SalaryId() {
    }

    public SalaryId(int empNo, Date fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryId salaryId = (SalaryId) o;
        return empNo == salaryId.empNo && Objects.equals(fromDate, salaryId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
