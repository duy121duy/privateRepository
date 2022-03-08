package BaiTap1.Bai2;

import BaiTap1.Bai4.Point;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bai2 {
    public static List<String> readFileText() throws IOException {
        BufferedReader bufferedReader = null;
        List<String> strings = new ArrayList<>();
        Reader reader = new FileReader("BaiTapPhan1/input_2/01.txt");
        bufferedReader = new BufferedReader(reader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            strings.add(str);
        }

        if (reader != null) {
            reader.close();
        }
        return strings;
    }

    public static void writeFileText(Map<String, Integer> words) throws IOException {
        FileWriter fw = new FileWriter("BaiTapPhan1/output_2/output.txt");
        BufferedWriter bw = new BufferedWriter(fw); //khai báo bộ nhớ đọc ghi
        for (String word : words.keySet()) {
            bw.write(word + " : " + words.get(word));
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    public static Map<String, Integer> countWords() throws IOException {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        List<String> words = new ArrayList<>();
        List<String> strings = readFileText();
        for (String s : strings) {
            StringTokenizer stringTokenizer = new StringTokenizer(s, " .,!=+-", false);
            while (stringTokenizer.hasMoreTokens()) {
                String str = stringTokenizer.nextToken();
                words.add(str);
            }
        }

        while (words.size() > 0) {
            String key = words.get(0);
            stringIntegerMap.put(key, Collections.frequency(words, key));
            words.removeAll(Stream.of(key).collect(Collectors.toList()));
        }
        return stringIntegerMap;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Integer> map = countWords();
        for (String key : map.keySet()) {
            System.out.println(key + " - " + map.get(key));
        }
        writeFileText(map);
    }
}
