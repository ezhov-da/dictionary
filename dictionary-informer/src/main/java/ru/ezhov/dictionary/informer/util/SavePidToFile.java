package ru.ezhov.dictionary.informer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SavePidToFile implements Saver {
    private final Pid pid;
    private final File fileSavePid;

    public SavePidToFile(Pid pid, String pathToFile) {
        this(pid, new File(pathToFile));
    }

    public SavePidToFile(Pid pid, File fileSavePid) {
        this.pid = pid;
        this.fileSavePid = fileSavePid;
    }

    public void save() throws SaveException {
        try {
            Files.write(fileSavePid.toPath(), pid.get().getBytes());
        } catch (PidException | IOException e) {
            throw new SaveException(e);
        }
    }
}
