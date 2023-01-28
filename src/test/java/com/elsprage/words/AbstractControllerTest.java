package com.elsprage.words;

import com.elsprage.words.persistance.repository.LanguageRepository;
import com.elsprage.words.persistance.repository.WordRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = WordsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {
    @MockBean
    private LanguageRepository languageRepository;
    @MockBean
    private WordRepository wordRepository;
}
