package ru.ezhov.dictionary.informer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.util.logging.Logger;

public class PidHolder {
    private static final Logger LOG = Logger.getLogger(PidHolder.class.getName());

    public static boolean savePid() {
        String s = ManagementFactory.getRuntimeMXBean().getName();
        if (!s.contains("@")) {
            JOptionPane.showMessageDialog(null, "Error get PID", "OOPS", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            try {
                Files.write(new File("pid.txt").toPath(), s.substring(0, s.indexOf("@")).getBytes());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Not write PID to file pid.txt", "OOPS", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
