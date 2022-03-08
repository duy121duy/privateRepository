package BaiTap1.Bai7;

import java.util.regex.Pattern;

public class Bai7 {
    private static String URL_PATTERN = "^(http|https)://((www|\\w+).)*[\\w]+.[a-z]{2,6}(/[\\S]*|/?)";
    public static boolean checkURL(String url) {
        return Pattern.matches(URL_PATTERN, url);
    }

    public static void main(String[] args) {
        System.out.println(checkURL("https://w1.w2.tiki.vn/dien-thoai-may-tinhbang/c1789?src=mega-menu"));
    }
}
