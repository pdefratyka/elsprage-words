package com.elsprage.words.utils;

import java.util.Base64;

public final class ImageUtils {

    public static String encodeImage(byte[] imageData) {
        if (imageData == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(imageData);
    }
}
