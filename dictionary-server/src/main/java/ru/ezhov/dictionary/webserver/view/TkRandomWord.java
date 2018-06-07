package ru.ezhov.dictionary.webserver.view;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqSocket;
import org.takes.rs.RsText;
import ru.ezhov.dictionary.webserver.dao.WordGeneratorDao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class TkRandomWord implements Take {
    private static final Logger LOG = Logger.getLogger(TkRandomWord.class.getName());

    private WordGeneratorDao wordGeneratorDao;

    public TkRandomWord(WordGeneratorDao wordGeneratorDao) {
        this.wordGeneratorDao = wordGeneratorDao;
    }

    public Response act(Request request) throws IOException {
        System.out.println(this.hashCode());

        String host = new RqSocket(request).getRemoteAddress().getHostAddress();
        System.out.println("connection host: " + host);

        try {
            String text = wordGeneratorDao.getRandomWord();
            System.out.println("word: " + text);

            return new RsText(String.format("{'text':'%s'}", text.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            return new RsText(e.getMessage());
        }
    }
}
