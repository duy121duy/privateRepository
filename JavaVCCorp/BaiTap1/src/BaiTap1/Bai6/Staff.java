package BaiTap1.Bai6;

import java.util.Date;

public class Staff {
    private int id;
    private boolean sex;
    private Date workDay;

    public Staff() {
    }

    public Staff(int id, boolean sex, Date workDay) {
        this.id = id;
        this.sex = sex;
        this.workDay = workDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Date workDay) {
        this.workDay = workDay;
    }
}
