package com.protocol.edm;

public enum ServerResponseProtocol {
    OK("OK"),
    SUCCEED_CONTAINS_DATA_RESPONSE("OKWITHDATA");

    private final String serverResponse;

    ServerResponseProtocol(String serverResponse) {
        this.serverResponse = serverResponse;
    }
}
