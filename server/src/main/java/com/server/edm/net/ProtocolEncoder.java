package com.server.edm.net;

import java.nio.charset.Charset;

public class ProtocolEncoder implements Encoder {
    private final Charset CHARSET = com.protocol.edm.Encoder.CHARSET;

    @Override
    public String decode(final byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    @Override
    public byte[] encode(final String message) {
        return message.getBytes(CHARSET);
    }
}
