package test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Bill bill1 = new Bill(1, 100000);
        Bill bill2 = new Bill(2, 100000);
        Customer customer = new Customer(1, "Duy", Stream.of(bill1, bill2).collect(Collectors.toList()));
        bill2.setCustomer(customer);
        bill1.setCustomer(customer);
        System.out.println(bill1.getCustomer());
    }
}
