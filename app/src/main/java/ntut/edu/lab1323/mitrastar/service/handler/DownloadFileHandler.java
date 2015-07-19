package ntut.edu.lab1323.mitrastar.service.handler;


import android.net.Uri;

import org.eclipse.jetty.server.Request;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.R;
import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.view.MainActivity;


public class DownloadFileHandler extends HttpBaseHandler {
    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException {

        File file = new File("/storage/emulated/0/DCIM/100MEDIA/IMAG2201.jpg");
        ServletOutputStream ous = null;
        InputStream ios = null;

        try {
            byte[] buffer = new byte[4096];
            ous = httpResponse.getOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
    }

}
