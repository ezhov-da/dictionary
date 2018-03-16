package ru.ezhov.dictionary.informer.dao;

import java.util.Random;

public class SimpleWordGeneratorDao implements WordGeneratorDao {

    private WordDao wordDao;

    public SimpleWordGeneratorDao(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    public String getRandomWord() throws Exception {
        try {
            if (wordDao.isChange()) {
                wordDao.reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wordDao.getWord(new Random().nextInt(wordDao.count()));
    }
}
