package com.server.edm.net;

public interface Encoder {
    String decode(final byte[] bytes);

    byte[] encode(final String message);
}
