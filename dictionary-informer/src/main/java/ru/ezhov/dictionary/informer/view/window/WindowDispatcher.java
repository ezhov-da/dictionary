package ru.ezhov.dictionary.informer.view.window;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class WindowDispatcher implements Runnable {
    private static final Logger LOG = Logger.getLogger(WindowDispatcher.class.getName());

    private static final int PLUS_X = 10;
    private static final int PLUS_Y = 10;

    private WindowsGenerator windowsGenerator;

    public WindowDispatcher(WindowsGenerator windowsGenerator) {
        this.windowsGenerator = windowsGenerator;
    }

    @Override
    public void run() {
        Point lastPoint = null;
        JWindow windowLast = windowsGenerator.generate();

        windowLast.pack();
        windowLast.setVisible(true);

        while (true) {
            try {
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();

                if (pointerInfo == null) {
                    continue;
                }

                Point point = MouseInfo.getPointerInfo().getLocation();

                JWindow window = windowsGenerator.generate();

                //Здесь мы просто сравниваем изменение окна
                // и отображаем новое коно, а старое убиваем
                if (windowIsChange(windowLast, window)) {
                    windowLast.setVisible(false);
                    windowLast.dispose();

                    windowLast = window;
                    setLocation(windowLast, point);
                    windowLast.pack();
                    windowLast.setVisible(true);
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!point.equals(lastPoint)) {
                    setLocation(windowLast, point);
                    lastPoint = point;
                }
            } catch (NullPointerException e) {
                //ignore
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private boolean windowIsChange(JWindow windowLast, JWindow windowCurrent) {
        return windowLast != null && windowLast != windowCurrent;
    }

    private void setLocation(JWindow window, Point point) {
        window.setLocation(point.x + PLUS_X, point.y + PLUS_Y);
    }

}
