import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogService {

    private static LogService instance;
    private static final String FILE_NAME = "actiuni_log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LogService() {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
        } catch (IOException e) {
            System.err.println("Eroare la initializarea fisierului log: " + e.getMessage());
        }
    }

    public static synchronized LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    public synchronized void log(String numeActiune) {
        String timestamp = LocalDateTime.now().format(formatter);
        String linie = numeActiune + "," + timestamp + "\n";

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(linie);
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in fisierul de log: " + e.getMessage());
        }
    }
}
