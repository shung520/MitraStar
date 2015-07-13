package ntut.edu.lab1323.mitrastar.service.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.eclipse.jetty.server.Request;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.view.MainActivity;

public class UploadFileHandler extends HttpBaseHandler {

    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, MainActivity activity) throws IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(request.getInputStream());
        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
        activity.showBitmap(bmp);

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
