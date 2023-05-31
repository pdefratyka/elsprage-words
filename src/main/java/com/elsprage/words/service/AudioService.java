package com.elsprage.words.service;

import com.elsprage.words.persistance.entity.Word;

public interface AudioService {
    void findAudioForWord(Word word);
}
