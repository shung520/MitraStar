package ntut.edu.lab1323.mitrastar.service;

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

import ntut.edu.lab1323.mitrastar.service.handler.UploadFileHandler;

public class HttpRouter {
    static final String LOG_TAG = "HttpRouter";
    private Map<String, AbstractHandler> handlers;

    public HttpRouter() {
        this.initHandlers();
    }

    private void initHandlers() {
        this.handlers = new HashMap<>();
        this.handlers.put("/", new UploadFileHandler());
    }

    public Handler createHandler() {
        return new AbstractHandler() {
            public void handle(String target,
                               Request request,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse) throws IOException, ServletException {
                Log.d(LOG_TAG, target);

                AbstractHandler handler = this.findHandler(target);
                if (handler != null) {
                    handler.handle(target, request, httpServletRequest, httpServletResponse);

                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

                }

                request.setHandled(true);
            }

            private AbstractHandler findHandler(String target) {
                for (String key : HttpRouter.this.handlers.keySet()) {
                    if (target.equals(key))
                        return HttpRouter.this.handlers.get(key);
                }
                return null;
            }
        };
    }
}
