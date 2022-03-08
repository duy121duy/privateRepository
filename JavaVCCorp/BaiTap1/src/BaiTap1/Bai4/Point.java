package BaiTap1.Bai4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Point {
    private int x;
    private int y;

    public static Set<Point> points1;
    public static Set<Point> points2;
    public static Set<Point> points3;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point A) {
        return Math.sqrt((this.x - A.x) * (this.x - A.x) + (this.y - A.y) * (this.y - A.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "BFSAlgorithm(" + x + "," + y + ")";
    }

    public static void writeFileText(String fileName, List<Point> points) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw); //khai báo bộ nhớ đọc ghi
        for (Point s: points) {
            bw.write(s.toString());
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    public static void init() {
        Point A = new Point(800,800);
        Point B = new Point(4000,800);
        Point C = new Point(2400,2400);
        points1 = new HashSet<>(8000);
        points2 = new HashSet<>(10000);
        points3 = new HashSet<>(12000);

        Random random = new Random();
        while (points1.size() < 8000) {
            Point point = new Point(random.nextInt(1200), random.nextInt(1200));
            if (point.distance(A) <= 400.0) {
                points1.add(point);
            }
        }

        while (points2.size() < 10000) {
            Point point = new Point(random.nextInt(4500), random.nextInt(1200));
            if (point.distance(B) <= 500.0) {
                if (!points1.contains(point)) {
                    points2.add(point);
                }
            }
        }

        while (points3.size() < 12000) {
            Point point = new Point(random.nextInt(3000), random.nextInt(3000));
            if (point.distance(C) <= 600.0) {
                if (!points1.contains(point) && !points2.contains(point)) {
                    points3.add(point);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        Set<Point> points = new HashSet<>();
        points.addAll(points1);
        points.addAll(points2);
        points.addAll(points3);
        System.out.println(points.size());
        List<Point> pointList = new ArrayList<>(points);
        Collections.shuffle(pointList);
        writeFileText("BaiTapPhan1/output_4/output4.txt", pointList);
    }
}
