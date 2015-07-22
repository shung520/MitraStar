package ntut.edu.lab1323.mitrastar.service;

import android.content.Context;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

public class HttpServer {
    static final int SERVER_PORT = 8080;
    private Server server;
    private HttpRouter router;
    private Context context;

    public HttpServer(Context context) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");

        this.context = context;

        this.server = new Server(SERVER_PORT);
        this.router = new HttpRouter(context);

        Handler handler = this.router.createHandler();
        this.server.setHandler(handler);
    }

    public HttpRouter getRouter() {
        return this.router;
    }

    public void start() {
        try {
            this.server.start();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
