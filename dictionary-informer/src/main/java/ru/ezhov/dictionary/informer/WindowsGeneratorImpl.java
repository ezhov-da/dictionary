package ru.ezhov.dictionary.informer;

import ru.ezhov.dictionary.informer.dao.WordDao;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class WindowsGeneratorImpl implements WindowsGenerator {
    private static final Logger LOG = Logger.getLogger(WindowsGeneratorImpl.class.getName());

    private WordWindow window = null;

    private WordDao wordDao;

    public WindowsGeneratorImpl(WordDao wordDao) {
        this.wordDao = wordDao;
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
        try {
            wordDao.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-10);
        }

        return getNewWindows();
    }

    private void createAndStartThreadUpdate() {
        Thread thread = new Thread(new WindowControlRunnable());
        thread.start();
    }

    private WordWindow getNewWindows() {
        return new WordWindow();
    }

    private class WordWindow extends JWindow {
        private JLabel labelCurrentTime = new JLabel("");
        private JLabel labelTimeForUpdate = new JLabel("");
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

            JPanel panelLabels = new JPanel();
            panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.X_AXIS));

            Font font = labelCurrentTime.getFont();
            font = font.deriveFont(Float.valueOf((font.getSize() - 3) + ""));
            labelCurrentTime.setFont(font);
            labelTimeForUpdate.setFont(font);

            labelCurrentTime.setHorizontalAlignment(SwingConstants.LEFT);
            labelCurrentTime.setForeground(Color.GRAY);
            labelCurrentTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            panelLabels.add(labelCurrentTime);
            panelLabels.add(Box.createHorizontalStrut(5));
            panelLabels.add(Box.createHorizontalGlue());
            labelTimeForUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
            labelTimeForUpdate.setText("30 сек.");
            panelLabels.add(labelTimeForUpdate);
            panelLabels.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

            JPanel panelText = new JPanel(new BorderLayout());

            labelText.setHorizontalAlignment(SwingConstants.CENTER);
            try {
                labelText.setText(wordDao.getRandomWord());
            } catch (Exception e) {
                e.printStackTrace();
                labelText.setText("NO GET WORD :(");
            }
            panelText.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            panelText.add(labelText, BorderLayout.CENTER);

            panelBasic.add(panelText);
            panelBasic.add(panelLabels);

            add(panelBasic);

            setAlwaysOnTop(true);
        }

        public JLabel getLabelCurrentTime() {
            return labelCurrentTime;
        }

        public JLabel getLabelText() {
            return labelText;
        }

        public JLabel getLabelTimeForUpdate() {
            return labelTimeForUpdate;
        }
    }

    private class WindowControlRunnable implements Runnable {

        @Override
        public void run() {
            String lastDate = null;
            long lastTimeGenerate = System.currentTimeMillis();

            while (true) {
                long dif = System.currentTimeMillis() - lastTimeGenerate;
                SwingUtilities.invokeLater(() -> {
                    long time = (30000 - dif) / 1000;

                    if (time > 15) {
                        window.getLabelTimeForUpdate().setForeground(Color.GRAY);
                    } else if (time < 6) {
                        window.getLabelTimeForUpdate().setForeground(Color.RED);
                    } else {
                        window.getLabelTimeForUpdate().setForeground(Color.BLACK);
                    }
                    window.getLabelTimeForUpdate().setText(time + " сек.");
                });

                //если прошло больше 30 секунд, генерируем новое окно
                if (dif > 30001) {
                    window = getNewWindows();
                    lastTimeGenerate = System.currentTimeMillis();
                }

                try {
                    String date = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    if (!date.equals(lastDate)) {

                        SwingUtilities.invokeLater(() -> {
                            window.getLabelCurrentTime().setText(date);
                        });

                        lastDate = date;
                    }
                } catch (NullPointerException e) {
                    //ignore
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
