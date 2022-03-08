package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetHtml1 {
    public void getHtmlContent() throws IOException {
        Document document = Jsoup.parseBodyFragment(Jsoup.connect("https://dantri.com.vn/").get().html());
        Element body = document.body();
        Elements paragraphs = body.getAllElements();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        FileOutputStream fos = new FileOutputStream(dtf.format(now) + ".txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osw);

        for (Element paragraph : paragraphs) {
            writer.write(paragraph.text());
            writer.write("\n");
        }

        writer.flush();
        writer.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.sleep(10000);
        GetHtml1 getHtml = new GetHtml1();
        getHtml.getHtmlContent();
    }
}
