import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ApacheCsvWriterExample {
    public static void main(String[] args) throws IOException {
        String csvFile = "data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader("id", "name", "age"))) {
            Employee employee = new Employee(1, "Duy", 22);
            csvPrinter.printRecord(employee.getId(), employee.getName(), employee.getAge());
            Employee employee2 = new Employee(2, "Duy2", 22);
            csvPrinter.printRecord(employee2.getId(), employee2.getName(), employee2.getAge());
            csvPrinter.flush();
        }
    }
}
