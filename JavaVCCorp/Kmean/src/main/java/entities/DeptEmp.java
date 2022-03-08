package entities;

import entities.PK.DeptEmpId;
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
@IdClass(DeptEmpId.class)
@Table(name = "dept_emp")
public class DeptEmp implements Serializable {
    @Id
    @Column(name = "emp_no", nullable = false)
    private int empNo;

    @Id
    @Column(name = "dept_no", nullable = false)
    private String deptNo;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;
}
