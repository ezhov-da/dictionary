package ru.ezhov.dictionary.informer.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileWordDao implements WordDao {
    private static final Logger LOG = Logger.getLogger(FileWordDao.class.getName());

    private String pathToFileWithWord;
    private List<String> words = new ArrayList<>();
    private long fileSize;

    public FileWordDao(String pathToFileWithWord) {
        this.pathToFileWithWord = pathToFileWithWord;
    }

    public int count() {
        return words.size();
    }

    public void load() throws Exception {
        fileSize = getFileSize();
        try (Scanner scanner =
                     new Scanner(
                             new BufferedInputStream(
                                     new FileInputStream(pathToFileWithWord)
                             ),
                             "UTF-8"
                     );) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        }
    }

    public long getFileSize() {
        File file = new File(pathToFileWithWord);
        return file.length();
    }

    public void reload() throws Exception {
        words.removeAll(words);
        load();
    }

    @Override
    public List<String> getAll() {
        return words;
    }

    @Override
    public String getWord(int i) {
        return words.get(i);
    }

    public String getRandomWord() throws Exception {
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

    public boolean isChange() throws Exception {
        return getFileSize() != fileSize;
    }
}
