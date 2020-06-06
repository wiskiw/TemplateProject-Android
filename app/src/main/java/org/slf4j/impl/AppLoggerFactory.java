package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;

import by.yablonski.templateproject.app.logging.SLF4JLoggerAdapter;

/**
 * Custom logger factory
 *
 * @author Andrey Yablonsky on 31.01.2020
 */
public class AppLoggerFactory implements ILoggerFactory {

    private final HashMap<String, SLF4JLoggerAdapter> loggerMap = new HashMap<>();

    @Override
    public Logger getLogger(String name) {
        synchronized (loggerMap) {

            if (!loggerMap.containsKey(name)) {
                loggerMap.put(name, new SLF4JLoggerAdapter(name));
            }

            return loggerMap.get(name);
        }
    }

}
