package com.elsprage.words.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ImageService {
    byte[] getImageFromUrl(String imageUrl) throws IOException;
    byte[] getImage(String keyword) throws IOException;
}
