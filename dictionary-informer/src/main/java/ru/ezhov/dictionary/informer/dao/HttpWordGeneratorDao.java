package ru.ezhov.dictionary.informer.dao;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpWordGeneratorDao implements WordGeneratorDao {
    private static final Logger LOG = Logger.getLogger(HttpWordGeneratorDao.class.getName());


    private String urlWordHolder;

    public HttpWordGeneratorDao(String urlWordHolder) {
        this.urlWordHolder = urlWordHolder;
    }

    @Override
    public String getRandomWord() throws Exception {
        URL url = new URL(urlWordHolder);
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
