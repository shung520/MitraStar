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

public class UploadVideoFileHandler extends HttpBaseHandler {
    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException {

    }
}
