package ingSw_beans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogController {
    private static LogController instance;
    private static final String LOG_FILE_PATH = "C:\\Users\\aleor\\eclipse-workspace\\IngSW\\ScanIt\\web\\DBs\\logs.txt";

    private LogController() {
        // Costruttore privato
    }

    public static synchronized LogController getInstance() {
        if (instance == null) {
            instance = new LogController();
        }
        return instance;
    }

    public synchronized void writeLog(Log log) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            printWriter.println(log.toString());
            printWriter.flush(); // Ensure data is written immediately
        } catch (IOException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
        }
    }

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log log = parseLog(line);
                if (log != null) {
                    logs.add(log);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
        }
        return logs;
    }

    public List<Log> getLogsByDate(String date) {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log log = parseLog(line);
                if (log != null && log.getTimestampAMD().equals(date)) {
                    logs.add(log);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
        }
        return logs;
    }

    private Log parseLog(String line) {
        try {
            String[] parts = line.split("\\|");
            String username = parts[0].trim();
            String operazione = parts[1].trim();
            String esito = parts[2].trim();
            String dateString = parts[3].trim();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = dateFormat.parse(dateString);

            long timestamp = date.getTime();
            return new Log(username, operazione, esito, timestamp);
        } catch (Exception e) {
            e.printStackTrace(); // Gestisci l'eccezione nel parsing
            return null;
        }
    }
}