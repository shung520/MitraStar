package ntut.edu.lab1323.mitrastar.service;

import org.eclipse.jetty.server.Request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.view.MainActivity;

public abstract class HttpBaseHandler {
    public abstract void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException;

    public boolean isAcceptRequestMethod() {
        return true;
    }
}
