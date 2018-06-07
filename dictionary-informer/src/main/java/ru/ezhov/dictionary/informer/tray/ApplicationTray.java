package ru.ezhov.dictionary.informer.tray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ApplicationTray {

    private JFrame frame;

    public ApplicationTray(JFrame frame) {
        this.frame = frame;
    }

    public void init() throws AWTException, IOException {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();

            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/dictionary_16x16.png")));

            trayIcon.addActionListener(e -> frame.setVisible(true));

            systemTray.add(trayIcon);
        }
    }
}
