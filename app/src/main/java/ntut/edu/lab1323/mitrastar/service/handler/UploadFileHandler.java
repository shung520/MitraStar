package ntut.edu.lab1323.mitrastar.service.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.eclipse.jetty.server.Request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.HttpBaseHandler;
import ntut.edu.lab1323.mitrastar.view.MainActivity;

public class UploadFileHandler extends HttpBaseHandler {

    @Override
    public void handle(Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse, final MainActivity activity) throws IOException {

        final Bitmap bmp = BitmapFactory.decodeStream(request.getInputStream());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showBitmap(bmp);
            }
        });

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
