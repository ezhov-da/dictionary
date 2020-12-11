package ru.ezhov.dictionary.informer.domain;

import java.util.Random;
import java.util.logging.Logger;

public class WordService {
    private static final Logger LOG = Logger.getLogger(WordService.class.getName());

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public String random() throws WordServiceException {
        try {
            if (wordRepository.isChange()) {
                wordRepository.reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wordRepository.getWord(new Random().nextInt(wordRepository.count()));
    }
}
