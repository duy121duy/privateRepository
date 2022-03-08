package BaiTap1.Bai6;

import java.util.Date;

public class SalesStaff extends Staff {
    private String shift;

    public SalesStaff() {
    }

    public SalesStaff(int id, boolean sex, Date workDay, String shift) {
        super(id, sex, workDay);
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
