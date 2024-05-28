package com.server.edm.ui;

import org.junit.jupiter.api.Test;

class ConsoleUITest {
    OutputUI serviceUI = new ConsoleOutputUI();

    @Test
    void visualizeLogo() {
        serviceUI.printLogo();
    }

}