package com.example.personal_finance_accounting.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class FileLogger {
     public static String FILE_PATH = "src/main/resources/logging/log.txt";



    public static void log(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDate.now()).append("|").append(message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(sb.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void clearAllLogs(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
