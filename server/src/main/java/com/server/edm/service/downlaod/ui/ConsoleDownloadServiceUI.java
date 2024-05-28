package com.server.edm.service.downlaod.ui;

import com.server.edm.ui.ConsoleOutputUI;
import com.server.edm.ui.InputUI;
import com.server.edm.ui.OutputUI;

public class ConsoleDownloadServiceUI implements DownloadServiceUI {
    private final OutputUI outputUI;
    private final InputUI inputUI;

    public ConsoleDownloadServiceUI(OutputUI console, InputUI inputUI) {
        this.outputUI = console;
        this.inputUI = inputUI;
    }

    @Override
    public String getUrl() {
        outputUI.printInfo("다운로드할 주소를 입력해주세요 : ");
        return inputUI.readLine();
    }

    @Override
    public String getFileName() {
        outputUI.printInfo("파일의 이름을 지정해주세요");
        return inputUI.readLine();
    }
}
