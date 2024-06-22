package ingSw_beans;

import java.io.BufferedReader;
import java.io.File;
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
    private PrintWriter printWriter;

    private LogController() throws IOException {
        File logFile = new File(LOG_FILE_PATH);
        printWriter = new PrintWriter(new FileWriter(logFile, true)); // Append mode
    }

    public static LogController getInstance() throws IOException {
        if (instance == null) {
            instance = new LogController();
        }
        return instance;
    }

    public void writeLog(Log log) {
        printWriter.println(log.toString());
        printWriter.flush(); // Ensure data is written immediately
    }


    public void closeLogFile() {
        if (printWriter != null) {
            printWriter.close();
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
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato nel tuo caso
        }
        return logs;
    }
    
    public List<Log> getLogsByDate(String date) {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log log = parseLog(line);
                if (log != null) {
                    
                    if (log.getTimestampAMD().equals(date)) {
                        logs.add(log);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato nel tuo caso
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
