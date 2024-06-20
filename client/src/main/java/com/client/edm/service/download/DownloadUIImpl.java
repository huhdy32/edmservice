package com.client.edm.service.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DownloadUIImpl implements DownloadUI {
    @Override
    public String getDirectoryPath() {
        System.out.println("패쓰 입력");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClientName(String serverRequest) {
        System.out.println("이름 입력 + " + serverRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
