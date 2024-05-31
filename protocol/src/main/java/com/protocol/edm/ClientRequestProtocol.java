package com.protocol.edm;

import java.util.Arrays;

public enum ClientRequestProtocol {
    SERVICE_LIST("SERVICELIST"),
    SERVICE_MAPPING("MAPTOSERVICE"),
    REGISTER_SERVICE_CLIENT_NICKNAME("REGISTERNAME"),
    WAITING_FOR_START("IMWAITING");

    private final String instruction;

    ClientRequestProtocol(String instruction) {
        this.instruction = instruction;
    }

    public ClientRequestProtocol getProtocol(final String clientRequest) {
        return Arrays.stream(ClientRequestProtocol.values())
                .filter(clientRequestProtocol -> clientRequestProtocol.instruction.equals(clientRequest))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("그런 프로토콜은 존재하지 않음"));
    }
}
