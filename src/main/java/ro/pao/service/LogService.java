package ro.pao.service;

import ro.pao.model.records.LoggerRecord;

import java.security.Timestamp;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public interface LogService {
    void log(Level level, String action);

    LoggerRecord logIntoCsv(Level level, String message);
}
