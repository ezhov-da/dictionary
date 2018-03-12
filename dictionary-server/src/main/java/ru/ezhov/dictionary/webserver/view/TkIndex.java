package ru.ezhov.dictionary.webserver.view;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;
import org.takes.rs.RsVelocity;

import java.io.IOException;

public class TkIndex implements Take {
    @Override
    public Response act(Request request) throws IOException {
        return new RsHtml(new RsVelocity(this.getClass().getResource("/pages/index.html.vm")));
    }
}
