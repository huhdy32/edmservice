package com.server.edm.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class BufferBasedDataTransferManager implements DataTransferManager {

    private static final int BUFFER_SIZE = 512;

    @Override
    public byte[] sendAndRecieve(SocketChannel socketChannel, byte[] data, int byteCount) {
        sendOnly(socketChannel, data, byteCount);
        return recieveOnly(socketChannel);
    }

    @Override
    public void send(SocketChannel socketChannel, byte[] data, int byteCount) {
        sendOnly(socketChannel, data, byteCount);
    }

    private byte[] recieveOnly(final SocketChannel socketChannel) {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        List<Byte> receivedData = new ArrayList<>();
        boolean check = true;
        try {
            while (check && socketChannel.read(buffer) > 0) {
                buffer.flip();
                while(buffer.hasRemaining()) {
                    byte tempByte = buffer.get();
                    if ((char)tempByte == '\n') {
                        check = false;
                        break;
                    }
                    receivedData.add(tempByte);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listToArray(receivedData);
    }

    private byte[] listToArray(final List<Byte> receivedData) {
        final byte[] resultBuffer = new byte[receivedData.size()];
        for (int i = 0; i < resultBuffer.length; i++) {
            resultBuffer[i] = receivedData.get(i);
        }
        return resultBuffer;
    }

    private void sendOnly(final SocketChannel socketChannel, final byte[] data, final int byteCount) {
        try {
            socketChannel.write(ByteBuffer.wrap(data, 0, byteCount));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
