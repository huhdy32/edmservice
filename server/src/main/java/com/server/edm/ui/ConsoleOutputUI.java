package com.server.edm.ui;

import com.protocol.edm.logo.ApplicationInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.protocol.edm.log.Color.*;

public class ConsoleOutputUI implements OutputUI {

    private final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public void printLogo() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ApplicationInfo.getLogo());
        print(sb.toString());
    }

    @Override
    public void printInfo(final String info) {
        print(info);
    }

    private void print(final String data) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
