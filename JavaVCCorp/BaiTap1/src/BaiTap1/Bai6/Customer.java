package BaiTap1.Bai6;

public class Customer {
    private int id;
    private int age;
    private boolean sex;

    public Customer() {
    }

    public Customer(int id, int age, boolean sex) {
        this.id = id;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
