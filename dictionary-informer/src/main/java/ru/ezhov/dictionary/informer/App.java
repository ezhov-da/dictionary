package ru.ezhov.dictionary.informer;

import ru.ezhov.dictionary.informer.dao.HttpWordDao;

import javax.swing.*;
import java.util.logging.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        App app = new App();
        app.setLaF();
        app.savePid();
        app.start();
    }

    private void setLaF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //
        }
    }

    private void savePid() {
        if (!PidHolder.savePid()) {
            System.exit(-1);
        }
    }

    private void start() {
        Thread thread =
                new Thread(
                        new WindowDispatcher(
                                new WindowsGeneratorImpl(
                                        new HttpWordDao()
                                )
                        )
                );
        thread.start();
    }
}
