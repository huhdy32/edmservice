package com.server.edm.service.web;

import java.io.BufferedInputStream;

public record WebResource(
        BufferedInputStream inputStream,
        Long fileSize
) {
}
