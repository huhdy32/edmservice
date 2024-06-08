package com.protocol.edm;

import java.util.Arrays;

public enum DownloadCommand {
    /**
     * message Example
     * START_DOWNLOAD (file_size) (file_name)
     */
    START_DOWNLOAD("START_DOWNLOAD"),
    END_DOWNLOAD("END_DOWNLOAD");

    private final String command;

    DownloadCommand(String command) {
        this.command = command;
    }

    public static DownloadCommand getCommand(final String command) {
        return Arrays.stream(DownloadCommand.values())
                .filter(downloadCommand -> downloadCommand.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 명령"));
    }
}
