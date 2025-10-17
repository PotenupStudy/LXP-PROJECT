package com.lxp.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    private final Logger logger;

    public Logging(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String format, Object... args) {
        logger.info(format, args);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void debug(String format, Object... args) {
        logger.debug(format, args);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
