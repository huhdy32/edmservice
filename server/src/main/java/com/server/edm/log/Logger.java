package com.server.edm.log;

public interface Logger {
    void debug(final String message);
    void info(final String message);
    void warn(final String message);
    void error(final String message);
    void fatal(final String message);
}
