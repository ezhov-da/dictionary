package ru.ezhov.dictionary.pid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FilePidRepository implements PidRepository {
    private final Pid pid;
    private final File fileSavePid;

    public FilePidRepository(Pid pid, String pathToFile) {
        this(pid, new File(pathToFile));
    }

    public FilePidRepository(Pid pid, File fileSavePid) {
        this.pid = pid;
        this.fileSavePid = fileSavePid;
    }

    public void save() throws PidRepositoryException {
        try {
            Files.write(fileSavePid.toPath(), pid.get().getBytes());
        } catch (PidException | IOException e) {
            throw new PidRepositoryException(e);
        }
    }
}
