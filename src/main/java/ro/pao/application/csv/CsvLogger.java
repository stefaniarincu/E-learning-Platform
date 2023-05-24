package ro.pao.application.csv;
import ro.pao.model.records.LoggerRecord;
import ro.pao.service.impl.LogServiceImpl;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import static ro.pao.application.utils.Constants.CSV_PATH_WRITE;
import static ro.pao.application.utils.Constants.LOG_HEADER;

public class CsvLogger {
    private static CsvLogger INSTANCE;

    private CsvLogger() {
    }

    public static CsvLogger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CsvLogger();
        }

        return INSTANCE;
    }
    private static final CsvWriter CSV_WRITER = CsvWriter.getInstance();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String[] format(LoggerRecord record) {
        return new String[] {TIMESTAMP_FORMATTER.format(record.dateTime()), record.level().toString(), record.message()};
    }

    public void logAction(LoggerRecord record){
        try {
            CSV_WRITER.writeLine(format(record), Path.of(CSV_PATH_WRITE));
        } catch (Exception e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }
}
