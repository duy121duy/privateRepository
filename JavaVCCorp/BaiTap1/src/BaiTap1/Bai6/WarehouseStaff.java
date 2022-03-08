package BaiTap1.Bai6;

import java.util.Date;

public class WarehouseStaff extends Staff {
    private int seniority;

    public WarehouseStaff() {
    }

    public WarehouseStaff(int id, boolean sex, Date workDay, int seniority) {
        super(id, sex, workDay);
        this.seniority = seniority;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }
}
