package ru.ezhov.dictionary.pid;

public class StringPidRepository implements PidRepository {

    private final Pid pid;
    private String pidStr;

    public StringPidRepository(Pid pid) {
        this.pid = pid;
    }

    @Override
    public void save() throws PidRepositoryException {
        try {
            pidStr = pid.get();
        } catch (PidException e) {
            throw new PidRepositoryException(e);
        }
    }

    public String getPid() throws PidException {
        try {
            save();
            return pidStr;
        } catch (PidRepositoryException e) {
            throw new PidException(e);
        }
    }
}
