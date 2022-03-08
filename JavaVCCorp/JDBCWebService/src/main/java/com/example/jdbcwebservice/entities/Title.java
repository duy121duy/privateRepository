package com.example.jdbcwebservice.entities;

import com.example.jdbcwebservice.entities.PK.TitleId;
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
@IdClass(TitleId.class)
@Table(name = "titles")
public class Title implements Serializable {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;
}
