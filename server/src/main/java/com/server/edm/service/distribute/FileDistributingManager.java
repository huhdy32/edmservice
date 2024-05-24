package com.server.edm.service.distribute;

import java.io.BufferedInputStream;
import java.nio.channels.SocketChannel;
import java.util.Set;

public interface FileDistributingManager {
    void distribute(Set<SocketChannel> channels, BufferedInputStream fileInputStream, String fileName);
}
