package ntut.edu.lab1323.mitrastar.service.handler;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import org.eclipse.jetty.server.Request;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.view.MainActivity;


public class UploadAudioFileHandler extends HttpBaseHandler {

    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException {
        InputStream input = request.getInputStream();
        int bufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        audioTrack.play();

        byte[] bytes = new byte[1000];
        int length;
        while ((length = input.read(bytes)) >= 0) {
            audioTrack.write(bytes, 0, length);
        }

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
