package by.yablonski.templateproject.app.logging.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import by.yablonski.templateproject.app.logging.AndroidLogger;
import by.yablonski.templateproject.app.logging.LogEvent;
import by.yablonski.templateproject.app.logging.LogLevel;

public class FileLogger implements AndroidLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLogger.class);

    private static final String TIME_FORMAT = "HH:mm:ss.SSS dd.MM.yyyy Z";
    private static final String RECORD_TEMPLATE = "%s [%s] %s: %s";
    private static final String NEW_LINE = "\n";

    private final File logFile;

    public FileLogger(File logFile) {
        this.logFile = logFile;

        if (logFile == null) {
            LOGGER.warn("File logger initialization failed: logFile is null!");
        } else {
            logAppend(NEW_LINE);
        }
    }

    @Override
    public void log(LogLevel level, LogEvent event) {
        if (logFile == null) {
            return;
        }
        logAppend(eventToString(level, event));
    }

    private void logAppend(String string) {
        try {
            FileWriter fileWriter = new FileWriter(logFile, true);
            fileWriter.append(string)
                    .close();

        } catch (IOException e) {
            LOGGER.warn("Writing logs IO exception.", e);
        }
    }

    private String eventToString(LogLevel level, LogEvent event) {
        String time = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(new Date());
        String string = String.format(RECORD_TEMPLATE, time, levelToString(level), event.getName(), event.getMessage());

        if (event.getThrowable() != null) {
            string = string
                    .concat(NEW_LINE)
                    .concat(event.getThrowable().toString());
        }

        return string.concat(NEW_LINE);
    }

    private String levelToString(LogLevel level) {
        return level.name();
    }

}
