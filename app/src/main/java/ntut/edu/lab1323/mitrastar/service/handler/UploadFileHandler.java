package ntut.edu.lab1323.mitrastar.service.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.eclipse.jetty.server.Request;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;

public class UploadFileHandler extends HttpBaseHandler {

    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(request.getInputStream());
        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

        httpResponse.setContentType("text/html");
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println("<h1>Hello World</h1>");
    }
}
