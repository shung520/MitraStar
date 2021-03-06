package ntut.edu.lab1323.mitrastar.service;

import android.content.Context;
import android.util.Log;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.edu.lab1323.mitrastar.service.handler.DownloadFileHandler;
import ntut.edu.lab1323.mitrastar.service.handler.UploadVideoFileHandler;

public class HttpRouter {
    static final String LOG_TAG = "HttpRouter";
    private Map<String, Class> handlers;
    private Context context;

    public HttpRouter(Context context) {
        this.initHandlers();

        this.context = context;
    }

    private void initHandlers() {
        this.handlers = new HashMap<>();
        this.handlers.put("/upload/video", UploadVideoFileHandler.class);
        this.handlers.put("/download", DownloadFileHandler.class);
    }

    public Handler createHandler() {
        return new AbstractHandler() {
            public void handle(String target,
                               Request request,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse) throws IOException, ServletException {
                Log.d(LOG_TAG, target);

                HttpBaseHandler handler = HttpRouter.this.findHandler(target);
                if (handler != null && handler.isAcceptRequestMethod()) {
                    handler.handle(request, httpServletRequest, httpServletResponse);

                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

                }

                request.setHandled(true);
            }
        };
    }

    private HttpBaseHandler findHandler(String target) {
        for (String key : this.handlers.keySet()) {
            if (target.equals(key))
                try {
                    try {
                        HttpBaseHandler handler = (HttpBaseHandler) this.handlers.get(key).newInstance();
                        handler.setContext(this.context);
                        return handler;

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();

                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();

                }
        }
        return null;
    }
}
