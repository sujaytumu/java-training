package day_05.service;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class PayrollReportService {

    public void saveReport(List<String> records) throws IOException {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of("payroll-report.txt"))) {

            writer.write("ID | NAME | SALARY | BONUS | TAX | NET SALARY");
            writer.newLine();

            for (String record : records) {
                writer.write(record);
                writer.newLine();
            }
        }
    }
}