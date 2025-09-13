package utils;

import java.util.logging.Logger;

public class CustomLogger {
    private static final Logger logger = Logger.getLogger(CustomLogger.class.getName());

    public static void info(String message) {
        logger.info(message);
    }
}
