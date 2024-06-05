package com.client.edm.net;

import java.net.Socket;

public interface ConnectionFactory {
    Connection create(final Socket socket);
}
