package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.UserAccount;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileLogger {
     public static String FILE_PATH_WITHOUT_USER = "src/main/resources/logging/";




    /**
     * Логгирует сообщение в файл.
     *
     * @param message Сообщение для логирования.
     * @param user пользователь, с учетной записи которого прошла авторизация
     *
     */
     public static void log(UserAccount user, String message) {
        String FILE_PATH = FILE_PATH_WITHOUT_USER + user.getEmail() + "log.txt";
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDate.now()).append("|").append(message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(sb.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void clearAllLogs(UserAccount user){
        String FILE_PATH = FILE_PATH_WITHOUT_USER + user.getEmail() + "log.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Читает сообщения в файл.
     *

     * @param user пользователь, с учетной записи которого прошла авторизация
     * @return список записей в файле лога, доступного для этого пользователя
     */
    public static List<String> readLogs(UserAccount user){
        List<String> logEntries = new ArrayList<>();
        String FILE_PATH = FILE_PATH_WITHOUT_USER + user.getEmail() + "log.txt";
        File logFile = new File(FILE_PATH);
        if(logFile.exists()){

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    logEntries.add(line);
                }
            } catch (IOException e) {
                logEntries=null;
            }
        }

        return logEntries;
    }
}
