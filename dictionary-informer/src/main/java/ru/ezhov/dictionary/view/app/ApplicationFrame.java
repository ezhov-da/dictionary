package ru.ezhov.dictionary.view.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ApplicationFrame extends JFrame {

    public ApplicationFrame() throws HeadlessException {
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Dictionary");
        try {
            setIconImage(ImageIO.read(getClass().getResource("/dictionary_16x16.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
