package ntut.edu.lab1323.mitrastar.service;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import ntut.edu.lab1323.mitrastar.view.MainActivity;

public class HttpServer {
    static final int SERVER_PORT = 8080;
    private Server server;
    private HttpRouter router;

    public HttpServer(MainActivity mainActivity) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");

        this.server = new Server(SERVER_PORT);
        this.router = new HttpRouter();

        Handler handler = this.router.createHandler(mainActivity);
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
