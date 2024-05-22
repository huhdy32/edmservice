package com.server.edm.net;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UTFEncoder implements Encoder {
    private final Charset CHARSET = StandardCharsets.UTF_8;

    @Override
    public String decode(final byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    @Override
    public byte[] encode(final String message) {
        return message.getBytes(CHARSET);
    }
}
