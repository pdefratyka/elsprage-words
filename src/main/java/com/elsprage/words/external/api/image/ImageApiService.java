package com.elsprage.words.external.api.image;

import com.elsprage.words.model.response.ImageApiResponse;

public interface ImageApiService {
    ImageApiResponse getImage(String keyword);
}
