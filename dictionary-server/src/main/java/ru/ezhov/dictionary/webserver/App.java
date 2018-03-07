package ru.ezhov.dictionary.webserver;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import ru.ezhov.dictionary.webserver.dao.FileWordDao;

public final class App {
    public static void main(final String... args) throws Exception {
        new FtBasic(
                new TkFork(new FkRegex("/randomword", new RandomWordTake(new FileWordDao("words.txt")))), 10101
        ).start(Exit.NEVER);
    }
}
