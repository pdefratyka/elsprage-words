package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.WordMapper;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.ImageService;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class WordsServiceImpl implements WordsService {
    private static final String EN_SYMBOL = "en";
    private final WordRepository wordRepository;
    private final ImageService imageService;
    private final WordMapper wordMapper;

    @Override
    public WordDTO saveWord(final WordDTO word) {
        final byte[] convertedImage = getConvertedImage(word);
        word.setImageData(convertedImage);
        Word savedWord = wordRepository.save(wordMapper.mapToWord(word));
        return wordMapper.mapToWordDTO(savedWord);
    }

    @Override
    public List<WordDTO> getAllWords() {
        final List<Word> words = wordRepository.findAll();
        return wordMapper.mapToWordsDTO(words);
    }

    private byte[] getConvertedImage(WordDTO word) {
        if (!ObjectUtils.isEmpty(word.getImage())) {
            return getImageFromUrl(word.getImage());
        } else {
            return getImageByKeyword(word);
        }
    }

    private byte[] getImageFromUrl(String image) {
        if (!ObjectUtils.isEmpty(image)) {
            try {
                return imageService.getImageFromUrl(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private byte[] getImageByKeyword(WordDTO word) {
        final String keyword = getKeywordFromImage(word);
        if (!ObjectUtils.isEmpty(keyword)) {
            try {
                return imageService.getImage(keyword);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private String getKeywordFromImage(WordDTO wordDTO) {
        if (EN_SYMBOL.equals(wordDTO.getTranslationLanguage().getSymbol())) {
            return wordDTO.getTranslation();
        } else if (EN_SYMBOL.equals(wordDTO.getValueLanguage().getSymbol())) {
            return wordDTO.getValue();
        }
        return null;
    }
}
