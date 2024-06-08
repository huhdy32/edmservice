package com.server.edm.service.downlaod.distribute;

import com.server.edm.service.WebResource;

import java.io.BufferedInputStream;
import java.nio.channels.SocketChannel;
import java.util.Set;

public interface FileDistributingManager {
    void distribute(Set<SocketChannel> channels, WebResource webResource, String fileName);
}
