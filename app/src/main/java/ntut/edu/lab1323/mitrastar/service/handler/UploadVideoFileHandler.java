package ntut.edu.lab1323.mitrastar.service.handler;

import org.eclipse.jetty.server.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.service.task.PlayVideoTask;
import ntut.edu.lab1323.mitrastar.view.MainActivity;

public class UploadVideoFileHandler extends HttpBaseHandler {
    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, final MainActivity activity) throws IOException {
        InputStream input = request.getInputStream();

        Calendar calendar = Calendar.getInstance();
        File outputDir = activity.getCacheDir();
        final File tempFile = File.createTempFile(Long.toString(calendar.getTime().getTime()), ".video", outputDir);
        FileOutputStream fos = new FileOutputStream(tempFile);

        int length;
        byte[] bytes = new byte[1000];
        try {
            while (true) {
                length = input.read(bytes);
                if (length < 0)
                    break;

                fos.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fos.close();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showVideo(tempFile);
            }
        });

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
