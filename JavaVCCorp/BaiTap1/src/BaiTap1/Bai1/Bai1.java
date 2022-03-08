package BaiTap1.Bai1;

import java.util.*;

public class Bai1 {
    public static Set<Integer> integers1;
    public static Set<Integer> integers2;

    public Bai1() {
        this.integers1 = new HashSet<>(2000000);
        this.integers2 = new HashSet<>(2000000);
        Random random = new Random();
        while(integers1.size() < 2000000) {
            integers1.add(random.nextInt(10000000));
        }

        while(integers2.size() < 2000000) {
            integers2.add(random.nextInt(10000000));
        }
    }

    public static Set<Integer> findUnion() {
        Set<Integer> integerSetUnion = new HashSet<>();
        integerSetUnion.addAll(integers1);
        integerSetUnion.addAll(integers2);
        return integerSetUnion;
    }

    public static Set<Integer> findUnique() {
        Set<Integer> integerSetUnique = new HashSet<>();
        integerSetUnique.addAll(integers1);
        integerSetUnique.retainAll(integers2);
        return integerSetUnique;
    }

    public static void main(String[] args) {
        Bai1 bai1 = new Bai1();

        System.out.println(bai1.integers1.size());
        System.out.println(bai1.integers2.size());
        final long startTime = System.currentTimeMillis();

        System.out.println(findUnion().size());
        System.out.println(findUnique().size());

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }
}
