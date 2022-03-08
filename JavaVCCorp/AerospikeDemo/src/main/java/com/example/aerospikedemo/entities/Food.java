package com.example.aerospikedemo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "food")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "inventory_number")
    private Integer inventoryNumber;

    @Column(name = "product_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date productDate;

    @Column(name = "expire_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expireDate;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "deleted")
    private boolean deleted;

    public Food(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
