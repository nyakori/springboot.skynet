package com.kaos.skynet.controller;

public final class MediaType {
    /**
     * contentType - application/json
     */
    public final static String JSON = "application/json;charset=UTF-8";

    /**
     * contentType - text/plain
     */
    public final static String TEXT = "text/plain;charset=UTF-8";

    /**
     * contentType - image/jpeg
     */
    public final static String JPEG = org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

    /**
     * contentType - image/png
     */
    public final static String PNG = org.springframework.http.MediaType.IMAGE_PNG_VALUE;
}
