package com.example.baitap2_1.entities;

import java.util.Set;

public class Data {
    private int user;
    private double per_user;
    private int user_old_time;
    private Set<Source> source;

    public Data(int user, double per_user, int user_old_time, Set<Source> source) {
        this.user = user;
        this.per_user = per_user;
        this.user_old_time = user_old_time;
        this.source = source;
    }

    public Data() {
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public double getPer_user() {
        return per_user;
    }

    public void setPer_user(double per_user) {
        this.per_user = per_user;
    }

    public int getUser_old_time() {
        return user_old_time;
    }

    public void setUser_old_time(int user_old_time) {
        this.user_old_time = user_old_time;
    }

    public Set<Source> getSource() {
        return source;
    }

    public void setSource(Set<Source> source) {
        this.source = source;
    }
}
