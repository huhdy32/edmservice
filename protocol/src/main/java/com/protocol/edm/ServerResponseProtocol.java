package com.protocol.edm;

public enum ServerResponseProtocol {
    OK("OK"),
    DOWNLOAD_SERVICE("DOWNLOAD_READY_PLEASE"),
    DOWNLOAD_STARTED("DOWNLOAD_STARTED_WITH_FILENAME"),
    STREAMING_SERVICE("STREAMING_READY_PLEASE");

    private final String serverResponse;

    ServerResponseProtocol(String serverResponse) {
        this.serverResponse = serverResponse;
    }
}
