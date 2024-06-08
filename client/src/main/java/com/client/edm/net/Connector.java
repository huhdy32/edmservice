package com.client.edm.net;

import com.client.edm.service.mapper.ServiceMapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connector implements Runnable {
    private final ConnectionFactory connectionFactory;
    private final ServiceMapper serviceMapper;
    private final ConnectionTarget connectionTarget;

    public Connector(ConnectionFactory connectionFactory, ServiceMapper serviceMapper, ConnectionTarget connectionTarget) {
        this.connectionFactory = connectionFactory;
        this.serviceMapper = serviceMapper;
        this.connectionTarget = connectionTarget;
    }

    @Override
    public void run() {
        final Socket socket = new Socket();
        System.out.println(socket.getLocalPort());
        try {
            socket.connect(new InetSocketAddress(connectionTarget.address(), connectionTarget.port()));
            final Connection connection = connectionFactory.create(socket);
            serviceMapper.mapToService(connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
