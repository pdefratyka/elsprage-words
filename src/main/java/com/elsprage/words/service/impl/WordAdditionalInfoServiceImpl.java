package com.elsprage.words.service.impl;

import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.service.AudioService;
import com.elsprage.words.service.ImageService;
import com.elsprage.words.service.WordAdditionalInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WordAdditionalInfoServiceImpl implements WordAdditionalInfoService {

    private final AudioService audioService;
    private final ImageService imageService;

    @Override
    public void setAdditionalInfo(final Word word) {
        audioService.findAudioForWord(word);
        imageService.findImageForWord(word);
    }
}
