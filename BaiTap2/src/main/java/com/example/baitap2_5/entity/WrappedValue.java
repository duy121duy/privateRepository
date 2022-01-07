package com.example.baitap2_5.entity;

public class WrappedValue<V> {
    private V value;
    private long TTLwrite;
    private long TTLread;

    public WrappedValue() {
    }

    public WrappedValue(V value) {
        this.value = value;
    }

    public WrappedValue(V value, long TTLwrite, long TTLread) {
        this.value = value;
        this.TTLwrite = TTLwrite;
        this.TTLread = TTLread;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getTTLwrite() {
        return TTLwrite;
    }

    public void setTTLwrite(long TTLwrite) {
        this.TTLwrite = TTLwrite;
    }

    public long getTTLread() {
        return TTLread;
    }

    public void setTTLread(long TTLread) {
        this.TTLread = TTLread;
    }
}
