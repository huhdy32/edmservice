package com.client.edm.net;

import java.io.*;
import java.net.Socket;

public class StreamConnection implements Connection {
    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public StreamConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public void send(String message) {
        try {
            System.out.println("SENDED : " + message);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(message + "\n");
            writer.flush();
            System.out.println("SENDED : SUCCEED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readLine() {
        try {
            System.out.println("읽기 시도중");
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }
}
