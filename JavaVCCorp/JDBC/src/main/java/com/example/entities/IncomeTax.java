package com.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(IncomeTaxId.class)
@Table(name = "income_tax")
public class IncomeTax implements Serializable {
    @Id
    @Column(name = "emp_no", nullable = false)
    private int empNo;

    @Column(name = "tax", nullable = false)
    private int tax;

    @Id
    @Column(name = "month", nullable = false)
    private int month;

    @Id
    @Column(name = "year", nullable = false)
    private int year;
}
