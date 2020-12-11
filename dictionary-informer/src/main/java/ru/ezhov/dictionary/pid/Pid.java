package ru.ezhov.dictionary.pid;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

public class Pid {
    private static final Logger LOG = Logger.getLogger(Pid.class.getName());

    public String get() throws PidException {
        String s = ManagementFactory.getRuntimeMXBean().getName();
        if (!s.contains("@")) {
            throw new PidException("Ошибка получения PID");
        }

        return s.substring(0, s.indexOf("@"));
    }
}
