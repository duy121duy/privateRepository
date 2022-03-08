import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApacheCsvReaderExample {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("data.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("id", "name", "age")
                .withIgnoreHeaderCase()
                .withTrim());

        int count = 0;

        for (CSVRecord csvRecord : csvParser) {
            //Printing the record
            if (count != 0) {
                int id = Integer.parseInt(csvRecord.get("id"));
                String name = csvRecord.get("name");
                int age = Integer.parseInt(csvRecord.get("age"));
                System.out.println("Record Number - " + csvRecord.getRecordNumber());
                System.out.println(new Employee(id, name, age));
            }
            count++;
        }
    }
}
