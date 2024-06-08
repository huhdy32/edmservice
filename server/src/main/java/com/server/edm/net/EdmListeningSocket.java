package com.server.edm.net;

import com.server.edm.distribute.ConnectionDistributor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * edmServer 의 클라이언트와의 연결 수립 담당
 */
public class EdmListeningSocket implements Runnable {
    private final ConnectionDistributor connectionDistributor;
    private final Selector selector;
    private final int port;
    private ServerSocketChannel serverSocketChannel;

    public EdmListeningSocket(final Selector selector, final int port, final ConnectionDistributor connectionDistributor) throws IOException {
        this.selector = selector;
        this.port = port;
        this.connectionDistributor = connectionDistributor;
        init();
    }

    private void init() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(this.port));
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.selector.select(selectionKey -> {
                    if (selectionKey.isAcceptable()) {
                        final ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        final SocketChannel channel;
                        try {
                            channel = socketChannel.accept();
                            channel.configureBlocking(true);
                            connectionDistributor.distribute(channel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocketChannel.close();
                selector.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
    }
}
