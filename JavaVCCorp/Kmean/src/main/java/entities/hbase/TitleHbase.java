package entities.hbase;

import java.util.Objects;

public class TitleHbase {
    private int empNo;

    private String title;

    private String duration;

    public TitleHbase() {
    }

    public TitleHbase(int empNo, String title, String duration) {
        this.empNo = empNo;
        this.title = title;
        this.duration = duration;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleHbase that = (TitleHbase) o;
        return empNo == that.empNo && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title);
    }
}
