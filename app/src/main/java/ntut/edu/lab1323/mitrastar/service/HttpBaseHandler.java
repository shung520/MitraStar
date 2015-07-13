package ntut.edu.lab1323.mitrastar.service;

import org.eclipse.jetty.server.Request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpBaseHandler {
    public abstract void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException;

    public boolean isAcceptRequestMethod() {
        return true;
    }
}
