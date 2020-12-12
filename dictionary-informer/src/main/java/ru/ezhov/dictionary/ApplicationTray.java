package ru.ezhov.dictionary;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ApplicationTray {

    private final JFrame frame;

    public ApplicationTray(JFrame frame) {
        this.frame = frame;
    }

    public void init() throws AWTException, IOException {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/dictionary_16x16.png")));
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
//                        frame.setVisible(!frame.isVisible());
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        System.exit(0);
                    }
                }
            });
            systemTray.add(trayIcon);
        }
    }
}
