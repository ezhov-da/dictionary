package ru.ezhov.dictionary.informer.dao;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpWordDao implements WordDao {
    private static final Logger LOG = Logger.getLogger(HttpWordDao.class.getName());

    @Override
    public void load() throws Exception {
        // not use
    }

    @Override
    public String getRandomWord() throws Exception {
//        URL url = new URL("http://localhost:10101/randomword");
        URL url = new URL("http://prog-tools.ru:10101/randomword");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int status = httpURLConnection.getResponseCode();
        String s;
        if (status == 200) {
            try (Scanner scanner =
                         new Scanner(
                                 new BufferedInputStream(httpURLConnection.getInputStream())
                                 , "UTF-8"
                         )
            ) {
                s = scanner.nextLine();
            }

        } else {
            throw new Exception("Ошибка подключения");
        }

        return s;
    }
}
