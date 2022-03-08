package BaiTap1.Bai3;

import BaiTap1.Bai2.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bai3 {
    public static List<String> readFileText(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        List<String> strings = new ArrayList<>();
        Reader reader = new FileReader(fileName);
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

    public static List<String> getFiles() throws IOException {
        return Files.walk(Paths.get("BaiTapPhan1/input_3"))
                .filter(Files::isRegularFile)
                .map(path -> path.toString()).collect(Collectors.toList());
    }

    public static Map<String, Integer> countWords(String fileName) throws IOException {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        List<String> words = new ArrayList<>();
        List<String> strings = readFileText(fileName);
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
        final long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(6);
        Map<String, Integer> totalWords = new HashMap<>();

        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        Future<Map<String, Integer>> future;
        Callable<Map<String, Integer>> callable;

        for (String filename : getFiles()) {
            callable = new Callable<Map<String, Integer>>() {
                @Override
                public Map<String, Integer> call() throws Exception {
                    return countWords(filename);
                }
            };

            future = executor.submit(callable);

            futures.add(future);
        }

        for (Future<Map<String, Integer>> future1 : futures) {
            try {
                Map<String, Integer> map = future1.get();
                map.keySet().stream().forEach(s -> {
                    int count = 0;
                    if (totalWords.containsKey(s)) {
                        count = totalWords.get(s) + map.get(s);
                    } else {
                        count = map.get(s);
                    }
                    totalWords.put(s, count);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<Word> words = totalWords.keySet().stream()
                .map(s -> new Word(s, totalWords.get(s)))
                .sorted((o1, o2) -> o2.getTotalCharacters() - o1.getTotalCharacters())
                .collect(Collectors.toList());

        System.out.println("Top 10 từ xuất hiện nhiều nhất : ");
        for (int i = 0; i < 10; i++) {
            System.out.println(words.get(i).getName() + " : " + words.get(i).getTotalCharacters());
        }

        System.out.println("Top 10 từ xuất hiện ít nhất : ");
        for (int i = words.size() - 1; i >= words.size() - 10; i--) {
            System.out.println(words.get(i).getName() + " : " + words.get(i).getTotalCharacters());
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }
}
