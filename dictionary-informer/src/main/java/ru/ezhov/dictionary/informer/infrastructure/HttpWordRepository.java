package ru.ezhov.dictionary.informer.infrastructure;

import ru.ezhov.dictionary.informer.domain.WordRepository;
import ru.ezhov.dictionary.informer.domain.WordRepositoryException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpWordRepository implements WordRepository {
    private static final Logger LOG = Logger.getLogger(HttpWordRepository.class.getName());

    private List<String> words = new ArrayList<>();

    private final String url;

    public HttpWordRepository(String url) {
        this.url = url;
    }

    @Override
    public int count() {
        return words.size();
    }

    @Override
    public boolean isChange() throws WordRepositoryException {
        return false;
    }

    @Override
    public void load() throws WordRepositoryException {
        List<String> tempWord = new ArrayList<>();
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int status = httpURLConnection.getResponseCode();
            if (status == 200) {
                try (Scanner scanner =
                             new Scanner(
                                     new BufferedInputStream(httpURLConnection.getInputStream()),
                                     "UTF-8"
                             )
                ) {
                    while (scanner.hasNext()) {
                        tempWord.add(scanner.nextLine());
                    }
                }
                words = tempWord;
            } else {
                throw new WordRepositoryException("Error connection. Status " + status);
            }
        } catch (IOException e) {
            throw new WordRepositoryException("Error load", e);
        }
    }

    @Override
    public void reload() throws WordRepositoryException {
        load();
    }

    @Override
    public List<String> all() {
        return words;
    }

    @Override
    public String getWord(int i) {
        return words.get(i);
    }
}
