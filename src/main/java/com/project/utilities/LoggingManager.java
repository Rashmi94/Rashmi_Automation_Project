package com.project.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingManager {

    public static Logger getLogging(Class<?> clazz)
    {
        return LogManager.getLogger();
    }
}
