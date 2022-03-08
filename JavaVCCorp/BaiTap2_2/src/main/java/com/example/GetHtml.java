package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetHtml {
    public void getHtmlContent() throws IOException {
        Document document = Jsoup.connect("https://dantri.com.vn/").get();
        Element body = document.body();
        Elements paragraphs = body.getAllElements();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
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

    public static void main(String[] args) throws IOException {
        GetHtml getHtml = new GetHtml();
        getHtml.getHtmlContent();
    }
}
