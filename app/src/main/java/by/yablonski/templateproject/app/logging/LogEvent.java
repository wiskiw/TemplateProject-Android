package by.yablonski.templateproject.app.logging;

import org.jetbrains.annotations.Nullable;

/**
 * @author Andrey Yablonsky on 31.01.2020
 */
public class LogEvent {

    private final String name;
    private final String message;
    private Throwable throwable;

    public LogEvent(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public LogEvent(String name, String message, Throwable throwable) {
        this(name, message);

        this.throwable = throwable;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }
}
