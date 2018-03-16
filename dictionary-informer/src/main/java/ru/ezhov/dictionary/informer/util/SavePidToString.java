package ru.ezhov.dictionary.informer.util;

public class SavePidToString implements Saver {

    private final Pid pid;
    private String pidStr;

    public SavePidToString(Pid pid) {
        this.pid = pid;
    }

    @Override
    public void save() throws SaveException {
        try {
            pidStr = pid.get();
        } catch (PidException e) {
            throw new SaveException(e);
        }
    }

    public String getPid() throws PidException {
        try {
            save();
            return pidStr;
        } catch (SaveException e) {
            throw new PidException(e);
        }
    }
}
