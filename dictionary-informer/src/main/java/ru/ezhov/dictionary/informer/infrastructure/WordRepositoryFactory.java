package ru.ezhov.dictionary.informer.infrastructure;

import ru.ezhov.dictionary.informer.domain.WordRepository;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class WordRepositoryFactory {
    private WordRepositoryFactory() {
    }

    public static WordRepository by(String source) throws WordRepositoryFactoryException {
        WordRepository wordRepository = null;

        File f = new File(source);
        if (f.exists() && !f.isDirectory()) {
            wordRepository = new FileWordRepository(source);
        } else {
            try {
                new URL(source);
                wordRepository = new HttpWordRepository(source);
            } catch (MalformedURLException ignored) {
            }
        }

        if (wordRepository == null) {
            throw new WordRepositoryFactoryException("Source '" + source + "' not a file or URL");
        }

        return wordRepository;
    }
}
