package com.loadbalancer.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class LoggerWrapper implements Logger {
    private final Logger logger;
    private final LoggerPanel loggerPanel;

    public LoggerWrapper(Logger logger, LoggerPanel loggerPanel) {
        this.logger = logger;
        this.loggerPanel = loggerPanel;
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {

    }

    @Override
    public void trace(String format, Object arg) {

    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(String format, Object... arguments) {

    }

    @Override
    public void trace(String msg, Throwable t) {

    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
        loggerPanel.log("[DEBUG] " + msg);
    }

    @Override
    public void debug(String format, Object arg) {
        logger.debug(format, arg);
        loggerPanel.log("[DEBUG] " + String.format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        logger.debug(format, arg1, arg2);
        loggerPanel.log("[DEBUG] " + String.format(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
        loggerPanel.log("[DEBUG] " + String.format(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        logger.debug(msg, t);
        loggerPanel.log("[DEBUG] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        logger.debug(marker, msg);
        loggerPanel.log("[DEBUG] " + msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        logger.debug(marker, format, arg);
        loggerPanel.log("[DEBUG] " + String.format(format, arg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        logger.debug(marker, format, arg1, arg2);
        loggerPanel.log("[DEBUG] " + String.format(format, arg1, arg2));
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        logger.debug(marker, format, arguments);
        loggerPanel.log("[DEBUG] " + String.format(format, arguments));
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        logger.debug(marker, msg, t);
        loggerPanel.log("[DEBUG] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
        loggerPanel.log("[INFO] " + msg);
    }

    @Override
    public void info(String format, Object arg) {
        logger.info(format, arg);
        loggerPanel.log("[INFO] " + String.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logger.info(format, arg1, arg2);
        loggerPanel.log("[INFO] " + String.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
        loggerPanel.log("[INFO] " + String.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        logger.info(msg, t);
        loggerPanel.log("[INFO] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    public void info(Marker marker, String msg) {
        logger.info(marker, msg);
        loggerPanel.log("[INFO] " + msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        logger.info(marker, format, arg);
        loggerPanel.log("[INFO] " + String.format(format, arg));
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        logger.info(marker, format, arg1, arg2);
        loggerPanel.log("[INFO] " + String.format(format, arg1, arg2));
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        logger.info(marker, format, arguments);
        loggerPanel.log("[INFO] " + String.format(format, arguments));
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        logger.info(marker, msg, t);
        loggerPanel.log("[INFO] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
        loggerPanel.log("[WARN] " + msg);
    }

    @Override
    public void warn(String format, Object arg) {
        logger.warn(format, arg);
        loggerPanel.log("[WARN] " + String.format(format, arg));
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
        loggerPanel.log("[WARN] " + String.format(format, arguments));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        logger.warn(format, arg1, arg2);
        loggerPanel.log("[WARN] " + String.format(format, arg1, arg2));
    }

    @Override
    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
        loggerPanel.log("[WARN] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {
        logger.warn(marker, msg);
        loggerPanel.log("[WARN] " + msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        logger.warn(marker, format, arg);
        loggerPanel.log("[WARN] " + String.format(format, arg));
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        logger.warn(marker, format, arguments);
        loggerPanel.log("[WARN] " + String.format(format, arguments));
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        logger.warn(marker, format, arg1, arg2);
        loggerPanel.log("[WARN] " + String.format(format, arg1, arg2));
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        logger.warn(marker, msg, t);
        loggerPanel.log("[WARN] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
        loggerPanel.log("[ERROR] " + msg);
    }

    @Override
    public void error(String format, Object arg) {
        logger.error(format, arg);
        loggerPanel.log("[ERROR] " + String.format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        logger.error(format, arg1, arg2);
        loggerPanel.log("[ERROR] " + String.format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
        loggerPanel.log("[ERROR] " + String.format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        logger.error(msg, t);
        loggerPanel.log("[ERROR] " + msg + " - " + t.getMessage());
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    public void error(Marker marker, String msg) {
        logger.error(marker, msg);
        loggerPanel.log("[ERROR] " + msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        logger.error(marker, format, arg);
        loggerPanel.log("[ERROR] " + String.format(format, arg));
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        logger.error(marker, format, arg1, arg2);
        loggerPanel.log("[ERROR] " + String.format(format, arg1, arg2));
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        logger.error(marker, format, arguments);
        loggerPanel.log("[ERROR] " + String.format(format, arguments));
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        logger.error(marker, msg, t);
        loggerPanel.log("[ERROR] " + msg + " - " + t.getMessage());
    }
}