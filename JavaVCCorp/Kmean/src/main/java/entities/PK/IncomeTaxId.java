package entities.PK;

import java.io.Serializable;
import java.util.Objects;

public class IncomeTaxId implements Serializable {
    private int empNo;

    private int month;

    private int year;

    public IncomeTaxId() {
    }

    public IncomeTaxId(int empNo, int month, int year) {
        this.empNo = empNo;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeTaxId that = (IncomeTaxId) o;
        return empNo == that.empNo && month == that.month && year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, month, year);
    }
}
