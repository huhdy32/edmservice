package com.client.edm.net;

import java.io.IOException;
import java.net.Socket;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Override
    public Connection create(Socket socket) {
        try {
            return new StreamConnection(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
