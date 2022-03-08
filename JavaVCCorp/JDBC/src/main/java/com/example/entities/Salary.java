package com.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SalaryId.class)
@Table(name = "salaries")
public class Salary implements Serializable {
    @Id
    @Column(name = "emp_no", nullable = false)
    private int empNo;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Id
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;
}
