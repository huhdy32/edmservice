package com.server.edm.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputUITest {
    InputUI inputUI = new ConsoleInputUI();
    @Test
    void test() {
        String temp = inputUI.readLine();
        System.out.println(temp);
    }

}