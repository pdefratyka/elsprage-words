package com.elsprage.words.service.impl;

import com.elsprage.words.external.api.image.ImageApiService;
import com.elsprage.words.model.response.ImageApiResponse;
import com.elsprage.words.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Service
@AllArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private ImageApiService imageApiService;

    @Override
    public byte[] getImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        return FileCopyUtils.copyToByteArray(connection.getInputStream());
    }

    @Override
    public byte[] getImage(String keyword) throws IOException {
        final String url = getUrlOfImage(keyword);
        if ("".equals(url)) {
            return null;
        }
        return getImageFromUrl(url);
    }

    private String getUrlOfImage(String keyword) {
        ImageApiResponse imageApiResponse = imageApiService.getImage(keyword);
        if (imageApiResponse.getPhotos().isEmpty()) {
            return "";
        }
        return imageApiResponse.getPhotos().get(0).getSrc().getMedium();
    }
}
