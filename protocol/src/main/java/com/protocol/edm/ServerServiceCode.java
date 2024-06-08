package com.protocol.edm;

import java.util.Arrays;

public enum ServerServiceCode {
    DOWNLOAD_SERVICE("DOWNLOAD_READY_PLEASE"),
    STREAMING_SERVICE("STREAMING_READY_PLEASE");

    private final String serverResponse;

    ServerServiceCode(String serverResponse) {
        this.serverResponse = serverResponse;
    }
    public String getServerResponse() {
        return this.serverResponse;
    }

    public static ServerServiceCode getServiceCode(final String serverResponse) {
        return Arrays.stream(ServerServiceCode.values())
                .filter(serverResponseProtocol -> serverResponseProtocol.serverResponse.equals(serverResponse))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("서비스로 매핑되지 않았습니다: " + serverResponse));
    }
}
