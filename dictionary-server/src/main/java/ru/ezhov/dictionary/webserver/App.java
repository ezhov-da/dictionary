package ru.ezhov.dictionary.webserver;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.tk.TkClasspath;
import ru.ezhov.dictionary.webserver.dao.FileWordDao;
import ru.ezhov.dictionary.webserver.dao.WordDao;
import ru.ezhov.dictionary.webserver.dao.WordGeneratorDaoImpl;
import ru.ezhov.dictionary.webserver.view.TkIndex;
import ru.ezhov.dictionary.webserver.view.TkRandomWord;

public final class App {
    public static void main(final String... args) throws Exception {

        WordDao wordDao = new FileWordDao("words.txt");
        try {
            wordDao.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1000);
        }

        new FtBasic(
                new TkFork(
                        new FkRegex("/", new TkIndex()),
                        new FkRegex("/js/.+", new TkClasspath()),
                        new FkRegex("/css/.+", new TkClasspath()),
                        new FkRegex("/randomword", new TkRandomWord(new WordGeneratorDaoImpl(wordDao)))
                ),
                10101
        ).start(Exit.NEVER);
    }
}
