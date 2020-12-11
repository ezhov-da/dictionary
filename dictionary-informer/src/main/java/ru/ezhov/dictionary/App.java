package ru.ezhov.dictionary;

import com.beust.jcommander.JCommander;
import ru.ezhov.dictionary.informer.domain.WordRepository;
import ru.ezhov.dictionary.informer.domain.WordRepositoryException;
import ru.ezhov.dictionary.informer.domain.WordService;
import ru.ezhov.dictionary.informer.infrastructure.HttpWordRepository;
import ru.ezhov.dictionary.informer.infrastructure.WordRepositoryFactory;
import ru.ezhov.dictionary.pid.Pid;
import ru.ezhov.dictionary.pid.PidRepositoryException;
import ru.ezhov.dictionary.pid.FilePidRepository;
import ru.ezhov.dictionary.view.app.ApplicationFrame;
import ru.ezhov.dictionary.view.window.WindowDispatcher;
import ru.ezhov.dictionary.view.window.WindowsGeneratorImpl;

import javax.swing.*;
import java.util.logging.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Args argsObject = new Args();

        JCommander.newBuilder()
                .addObject(argsObject)
                .build()
                .parse(args);

        App app = new App();
        app.savePid();
        app.setLaF();

        try {
            new ApplicationTray(new ApplicationFrame()).init();

            app.start(WordRepositoryFactory.by(argsObject.source()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setLaF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //
        }
    }

    private void savePid() {
        try {
            new FilePidRepository(new Pid(), "pid.txt").save();
        } catch (PidRepositoryException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void start(WordRepository wordRepository) throws WordRepositoryException {
        wordRepository.load();

        Thread thread =
                new Thread(
                        new WindowDispatcher(
                                new WindowsGeneratorImpl(
                                        new WordService(wordRepository)
                                )
                        )
                );
        thread.start();
    }
}
