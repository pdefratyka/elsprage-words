package com.elsprage.words.service;

import com.elsprage.words.model.request.WordRequest;

import java.io.IOException;

public interface ImageService {
    byte[] getImageFromUrl(String imageUrl) throws IOException;
    byte[] getImage(String keyword) throws IOException;
}
