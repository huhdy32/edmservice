package com.server.edm.service;

import java.io.BufferedInputStream;

public record WebResource(
        BufferedInputStream inputStream,
        Long fileSize
) {
}
