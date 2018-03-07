package ru.ezhov.dictionary.webserver;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqSocket;
import org.takes.rs.RsText;
import ru.ezhov.dictionary.webserver.dao.WordDao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class RandomWordTake implements Take {
    private static final Logger LOG = Logger.getLogger(RandomWordTake.class.getName());

    private WordDao wordDao;

    public RandomWordTake(WordDao wordDao) {
        this.wordDao = wordDao;
        try {
            wordDao.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1000);
        }
    }

    public Response act(Request request) throws IOException {
        String host = new RqSocket(request).getRemoteAddress().getHostAddress();
        System.out.println("connection host: " + host);

        try {
            String text = wordDao.getRandomWord();
            System.out.println("word: " + text);

            return new RsText(text.getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return new RsText(e.getMessage());
        }
    }
}
