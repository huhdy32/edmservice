package com.server.edm.log;

import com.server.edm.ui.ConsoleOutputUI;
import com.server.edm.ui.OutputUI;

import java.time.LocalDateTime;

public class ConsoleLogger implements Logger {
    private final OutputUI outputUI;

    public ConsoleLogger(OutputUI outputUI) {
        this.outputUI = outputUI;
    }

    private String matchFormat(Level level, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleOutputUI.blue + "[ EDM ] " + ConsoleOutputUI.exit + " [" + LocalDateTime.now() + "] : " + "[ " + level.levelMessage + " ] " +  message + "\n");
        return sb.toString();
    }

    @Override
    public void debug(String message) {
        outputUI.printInfo(matchFormat(Level.DEBUG, message));
    }

    @Override
    public void info(String message) {
        outputUI.printInfo(matchFormat(Level.INFO, message));

    }

    @Override
    public void warn(String message) {
        outputUI.printInfo(matchFormat(Level.WARN, message));
    }

    @Override
    public void error(String message) {
        outputUI.printInfo(matchFormat(Level.ERROR, message));
    }

    @Override
    public void fatal(String message) {
        outputUI.printInfo(matchFormat(Level.FATAL, message));
    }

    private enum Level {
        DEBUG(ConsoleOutputUI.white + "DEBUG" + ConsoleOutputUI.exit),
        INFO(ConsoleOutputUI.green + "DEBUG" + ConsoleOutputUI.exit),
        WARN(ConsoleOutputUI.yellow + "DEBUG" + ConsoleOutputUI.exit),
        ERROR(ConsoleOutputUI.yellow + "DEBUG" + ConsoleOutputUI.exit),
        FATAL(ConsoleOutputUI.red + "DEBUG" + ConsoleOutputUI.exit);

        private final String levelMessage;

        Level(String levelMessage) {
            this.levelMessage = levelMessage;
        }
    }
}
