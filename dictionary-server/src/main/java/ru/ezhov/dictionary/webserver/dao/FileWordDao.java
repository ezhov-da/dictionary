package ru.ezhov.dictionary.webserver.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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

    private long getFileSize() {
        File file = new File(pathToFileWithWord);
        return file.length();
    }

    public void reload() throws Exception {
        words.removeAll(words);
        load();
    }

    public boolean isChange() throws Exception {
        return getFileSize() != fileSize;
    }

    public List<String> getAll() {
        return words;
    }

    public String getWord(int i) {
        return words.get(i);
    }
}
