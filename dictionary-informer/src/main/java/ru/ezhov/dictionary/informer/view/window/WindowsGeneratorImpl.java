package ru.ezhov.dictionary.informer.view.window;

import ru.ezhov.dictionary.informer.dao.WordGeneratorDao;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class WindowsGeneratorImpl implements WindowsGenerator {
    private static final Logger LOG = Logger.getLogger(WindowsGeneratorImpl.class.getName());

    private WordWindow window = null;

    private WordGeneratorDao wordGeneratorDao;

    public WindowsGeneratorImpl(WordGeneratorDao wordGeneratorDao) {
        this.wordGeneratorDao = wordGeneratorDao;
    }

    @Override
    public JWindow generate() {
        if (window == null) {
            window = initWindow();
            createAndStartThreadUpdate();
        }

        return window;
    }

    private WordWindow initWindow() {
        return getNewWindows();
    }

    private void createAndStartThreadUpdate() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new WindowControlRunnable(), 30000, 30000);
    }

    private WordWindow getNewWindows() {
        return new WordWindow();
    }

    private class WordWindow extends JWindow {
        private JLabel labelText = new JLabel();

        public WordWindow() {
            setLayout(new BorderLayout());

            JPanel panelBasic = new JPanel();
            panelBasic.setLayout(new BoxLayout(panelBasic, BoxLayout.Y_AXIS));
            panelBasic.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(3, 3, 3, 3),
                            BorderFactory.createLineBorder(Color.GRAY))
            );

            JPanel panelText = new JPanel(new BorderLayout());

            labelText.setHorizontalAlignment(SwingConstants.CENTER);
            try {
                labelText.setText(wordGeneratorDao.getRandomWord());
            } catch (Exception e) {
                e.printStackTrace();
                labelText.setText("NO GET WORD :(");
            }
            panelText.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            panelText.add(labelText, BorderLayout.CENTER);

            panelBasic.add(panelText);

            add(panelBasic);

            setAlwaysOnTop(true);
        }
    }

    private class WindowControlRunnable extends TimerTask {

        @Override
        public void run() {
            window = getNewWindows();
        }
    }
}
