package ru.ezhov.dictionary.webserver.dao;

import java.util.Random;

public class WordGeneratorDaoImpl implements WordGeneratorDao {

    private WordDao wordDao;

    public WordGeneratorDaoImpl(WordDao wordDao) {
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
