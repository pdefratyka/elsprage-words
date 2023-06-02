package com.elsprage.words.service.impl;

import com.elsprage.words.exception.LanguageDoesNotExists;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.service.AudioService;
import com.elsprage.words.service.WordAdditionalInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class WordAdditionalInfoServiceImpl implements WordAdditionalInfoService {

    private final AudioService audioService;
    private final ImageServiceImpl imageService;

    @Override
    public void setAdditionalInfo(final Word word) {
        audioService.findAudioForWord(word);
        imageService.findImageForWord(word);
    }
}
