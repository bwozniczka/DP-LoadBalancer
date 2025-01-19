package com.loadbalancer.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerPanelFactory {
    private static final LoggerPanel loggerPanel = new LoggerPanel();

    public static Logger getLogger(Class<?> targetClass) {
        Logger logger = LoggerFactory.getLogger(targetClass);

        return new LoggerWrapper(logger, loggerPanel);
    }

    public static LoggerPanel getLoggerPanel() {
        return loggerPanel;
    }
}