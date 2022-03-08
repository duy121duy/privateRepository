package com.example.jpawebservice.entities.PK;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TitleId implements Serializable {
    private int empNo;

    private String title;

    private Date fromDate;

    public TitleId() {
    }

    public TitleId(int empNo, String title, Date fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleId titleId = (TitleId) o;
        return empNo == titleId.empNo && Objects.equals(title, titleId.title) && Objects.equals(fromDate, titleId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}
