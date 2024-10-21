package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.UserAccount;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileLogger {
     public static String FILE_PATH_WITHOUT_USER = "/logging/";




    /**
     * Логгирует сообщение в файл.
     *
     * @param message Сообщение для логирования.
     * @param user пользователь, с учетной записи которого прошла авторизация
     *
     */
     public static void log(UserAccount user, String message) {
//        String FILE_PATH = FILE_PATH_WITHOUT_USER + user.getEmail() + "log.txt";
         File logDir = new File("logging/");
         if (!logDir.exists()) {
             logDir.mkdirs();
         }
         String FILE_PATH = "logging/" + user.getEmail() + "log.txt";
//        StringBuilder sb = new StringBuilder();
//        sb.append(LocalDate.now()).append("|").append(message);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//            writer.write(sb.toString());
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }

         String logMessage = LocalDate.now() + "|" + message + System.lineSeparator();
         try (OutputStream out = new FileOutputStream(FILE_PATH, true)) {
             out.write(logMessage.getBytes());
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
     * Read messages into text file, but not for JAR-file
     *

     * @param user user with authentication
     * @return return list of logs' notes
     */
//    public static List<String> readLogs(UserAccount user){
//        List<String> logEntries = new ArrayList<>();
//        String FILE_PATH = FILE_PATH_WITHOUT_USER + user.getEmail() + "log.txt";
//        File logFile = new File(FILE_PATH);
//        if(logFile.exists()){
//
//            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    logEntries.add(line);
//                }
//            } catch (IOException e) {
//                logEntries=null;
//            }
//        }
//
//        return logEntries;
//    }

//    public static List<String> readLogs(UserAccount user) {
//        List<String> logEntries = new ArrayList<>();
//        InputStream in = getResourceAsStream("/logging/" + user.getEmail() + "log.txt");
//
//        try {
//            File tempFile = File.createTempFile(user.getEmail() + "log", ".txt");
//            try (FileOutputStream out = new FileOutputStream(tempFile)) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, bytesRead);
//                }
//            }
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    logEntries.add(line);
//                }
//            }
//
//            tempFile.deleteOnExit();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        return logEntries;
//    }

    public static List<String> readLogs(UserAccount user) {
        List<String> logEntries = new ArrayList<>();
        String filePath = "logging/" + user.getEmail() + "log.txt";
        File logDir = new File("logging/");
        File logFile = new File(filePath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("cant create file");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return logEntries;
    }

    private static InputStream getResourceAsStream(String filePath) {
        return FileLogger.class.getResourceAsStream(filePath);
    }

}
