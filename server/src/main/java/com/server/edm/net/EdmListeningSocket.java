package com.server.edm.net;

import com.server.edm.distribute.ConnectionDistributor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EdmListeningSocket implements Runnable {
    private final ConnectionDistributor connectionDistributor;
    private final Selector selector;
    private final int port;

    public EdmListeningSocket(final Selector selector, final int port, final ConnectionDistributor connectionDistributor) throws IOException {
        this.selector = selector;
        this.port = port;
        this.connectionDistributor = connectionDistributor;
        init();
    }

    private void init() throws IOException {
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(this.port));
        socketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.selector.select(selectionKey -> {
                    if (selectionKey.isAcceptable()) {
                        final ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        final SocketChannel channel;
                        try {
                            channel = socketChannel.accept();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        connectionDistributor.distribute(channel);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
