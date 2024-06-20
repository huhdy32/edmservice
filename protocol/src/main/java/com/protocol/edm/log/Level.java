package com.protocol.edm.log;

import static com.protocol.edm.log.Color.*;

public enum Level {
    DEBUG(white + "DEBUG" + exit),
    INFO(green + "DEBUG" + exit),
    WARN(yellow + "DEBUG" + exit),
    ERROR(yellow + "DEBUG" + exit),
    FATAL(red + "DEBUG" + exit);

    private final String levelMessage;

    Level(String levelMessage) {
        this.levelMessage = levelMessage;
    }
}