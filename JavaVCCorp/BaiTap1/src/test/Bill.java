package test;

import java.util.Objects;

public class Bill {
    private int id;
    private Customer customer;
    private double price;

    public Bill() {
    }

    public Bill(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public Bill(int id, Customer customer, double price) {
        this.id = id;
        this.customer = customer;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id && Double.compare(bill.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", customer=" + customer +
                ", price=" + price +
                '}';
    }
}
