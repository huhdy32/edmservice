package com.server.edm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StreamWebResourceAccessManagerTest {
    private WebResourceAccessManager accessManager = new StreamWebResourceAccessManager();

    @Test
    void successfulAccessTest() {
        BufferedInputStream inputStream = accessManager.access("https://www.google.com/imgres?q=사진&imgurl=https%3A%2F%2Fcdn.travie.com%2Fnews%2Fphoto%2Ffirst%2F201710%2Fimg_19975_1.jpg&imgrefurl=https%3A%2F%2Fwww.travie.com%2Fnews%2FarticleView.html%3Fidxno%3D19975&docid=D696xPT19OV0eM&tbnid=-MQcOiDkucl80M&vet=12ahUKEwi1qYXt2qWGAxUfoK8BHbR_A6IQM3oECB0QAA..i&w=800&h=538&hcb=2&ved=2ahUKEwi1qYXt2qWGAxUfoK8BHbR_A6IQM3oECB0QAA");
        Assertions.assertDoesNotThrow(() -> inputStream.read());
    }


}