package ntut.edu.lab1323.mitrastar.service.handler;


import org.eclipse.jetty.server.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.DownloadCache;
import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;


public class DownloadFileHandler extends HttpBaseHandler {

    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        String key = request.getParameter("key");
        File file = DownloadCache.getInstance().getFile(key);

        if (file == null) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ServletOutputStream outputStream = httpResponse.getOutputStream();
        InputStream inputStream = null;

        try {
            int read;
            byte[] buffer = new byte[4096];
            inputStream = new FileInputStream(file);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}
