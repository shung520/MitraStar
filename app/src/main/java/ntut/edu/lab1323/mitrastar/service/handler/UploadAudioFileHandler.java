package ntut.edu.lab1323.mitrastar.service.handler;

import android.media.MediaPlayer;
import android.util.Log;

import org.eclipse.jetty.server.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.view.MainActivity;


public class UploadAudioFileHandler extends HttpBaseHandler {

    @Override
    public void handle(final Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException {
        final InputStream input = request.getInputStream();

        Calendar calendar = Calendar.getInstance();
        File outputDir = activity.getCacheDir();
        final File tempFile = File.createTempFile(Long.toString(calendar.getTime().getTime()), ".audio", outputDir);
        FileOutputStream fos = new FileOutputStream(tempFile);

        int length;
        try {
            while (true) {
                byte[] bytes = new byte[1000];
                length = input.read(bytes);
                if (length < 0)
                    break;
                
                fos.write(bytes, 0, length);

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        fos.close();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();

                    FileInputStream fis = new FileInputStream(tempFile);
                    Log.d("UploadAudioFileHandler", "delete " + tempFile.getName() + " -> " + Boolean.toString(tempFile.delete()));

                    mediaPlayer.setDataSource(fis.getFD());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
        thread.start();

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
