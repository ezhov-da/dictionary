package ru.ezhov.dictionary;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = {"-s", "--source"}, description = "Source. File or URL", required = true)
    private String source = null;

    public String source() {
        return source;
    }
}