package com.server.edm.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputUI implements InputUI {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
