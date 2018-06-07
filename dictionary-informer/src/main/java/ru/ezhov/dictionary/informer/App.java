package ru.ezhov.dictionary.informer;

import ru.ezhov.dictionary.informer.dao.HttpWordGeneratorDao;
import ru.ezhov.dictionary.informer.tray.ApplicationTray;
import ru.ezhov.dictionary.informer.util.Pid;
import ru.ezhov.dictionary.informer.util.SaveException;
import ru.ezhov.dictionary.informer.util.SavePidToFile;
import ru.ezhov.dictionary.informer.view.app.ApplicationFrame;
import ru.ezhov.dictionary.informer.view.window.WindowDispatcher;
import ru.ezhov.dictionary.informer.view.window.WindowsGeneratorImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        App app = new App();
        app.savePid();
        app.setLaF();

        try {
            new ApplicationTray(new ApplicationFrame()).init();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        app.start();
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
            new SavePidToFile(new Pid(), "pid.txt").save();
        } catch (SaveException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void start() {
        Thread thread =
                new Thread(
                        new WindowDispatcher(
                                new WindowsGeneratorImpl(
                                        new HttpWordGeneratorDao("http://prog-tools.ru:10101/randomword")
                                )
                        )
                );
        thread.start();
    }
}
