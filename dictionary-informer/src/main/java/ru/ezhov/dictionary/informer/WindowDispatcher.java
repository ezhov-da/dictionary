package ru.ezhov.dictionary.informer;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class WindowDispatcher implements Runnable {
    private static final Logger LOG = Logger.getLogger(WindowDispatcher.class.getName());

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
                //Важно! Генератор окон сам отвечает за то,
                //когда необходимо менять окно и необходимо ли вообще
                //Здесь же мы просто сравниваем изменение и отображаем новое коно,
                // а старое убиваем
                if (windowLast != null && windowLast != window) {
                    windowLast.setVisible(false);
                    windowLast.dispose();

                    windowLast = window;
                    windowLast.setLocation(point.x + 10, point.y + 10);
                    windowLast.pack();
                    windowLast.setVisible(true);
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!point.equals(lastPoint)) {
                    windowLast.setLocation(point.x + 10, point.y + 10);
                    lastPoint = point;
                }
            } catch (NullPointerException e) {
                //ignore
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
