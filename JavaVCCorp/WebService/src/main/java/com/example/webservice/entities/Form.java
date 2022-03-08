package com.example.webservice.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Form implements Serializable {
    private int id;

    public Form() {
    }

    public Form(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
