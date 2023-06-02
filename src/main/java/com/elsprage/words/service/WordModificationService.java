package com.elsprage.words.service;

public interface WordModificationService {
    void setAudio(Long wordId, byte[] audioData);

    void setImage(Long wordId, byte[] imageData);
}
