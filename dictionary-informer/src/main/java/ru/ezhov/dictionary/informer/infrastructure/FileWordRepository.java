package ru.ezhov.dictionary.informer.infrastructure;

import ru.ezhov.dictionary.informer.domain.WordRepository;
import ru.ezhov.dictionary.informer.domain.WordRepositoryException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileWordRepository implements WordRepository {
    private static final Logger LOG = Logger.getLogger(FileWordRepository.class.getName());

    private final String pathToFileWithWord;
    private final List<String> words = new ArrayList<>();
    private long fileSize;

    public FileWordRepository(String pathToFileWithWord) {
        this.pathToFileWithWord = pathToFileWithWord;
    }

    public int count() {
        return words.size();
    }

    public void load() throws WordRepositoryException {
        fileSize = getFileSize();
        try (
                Scanner scanner =
                        new Scanner(
                                new BufferedInputStream(
                                        new FileInputStream(pathToFileWithWord)
                                ),
                                "UTF-8"
                        )
        ) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        } catch (Exception e){
            throw new WordRepositoryException("Error when load word from file", e);
        }
    }

    public long getFileSize() {
        File file = new File(pathToFileWithWord);
        return file.length();
    }

    public void reload() throws WordRepositoryException {
        words.clear();
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

    public String getRandomWord() throws WordRepositoryException {
        try {
            if (isChange()) {
                reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-500);
        }

        return words.get(new Random().nextInt(count()));
    }

    public boolean isChange() throws WordRepositoryException {
        return getFileSize() != fileSize;
    }
}
