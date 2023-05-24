package ro.pao.model.records;

import ro.pao.model.Test;

import java.time.LocalDateTime;
import java.util.logging.Level;

public record LoggerRecord (Level level, String message, LocalDateTime dateTime) { }