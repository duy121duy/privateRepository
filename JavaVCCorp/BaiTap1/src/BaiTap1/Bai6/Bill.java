package BaiTap1.Bai6;

import java.util.Date;

public class Bill {
    private int id;
    private Date boughtDate;
    private int staffId;
    private int customerId;
    private int totalPrice;

    public Bill() {
    }

    public Bill(int id, Date boughtDate, int staffId, int customerId, int totalPrice) {
        this.id = id;
        this.boughtDate = boughtDate;
        this.staffId = staffId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
