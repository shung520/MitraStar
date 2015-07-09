package ntut.edu.lab1323.mitrastar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainActivity extends ActionBarActivity {
    static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");

        Server webServer = new Server(8080);

        Handler handler = new AbstractHandler() {
            public void handle(String target, Request request, HttpServletRequest servletRequest,
                               HttpServletResponse servletResponse) throws IOException, ServletException {
                servletResponse.setContentType("text/html");
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                servletResponse.getWriter().println("<h1>Hello World</h1>");
                request.setHandled(true);
            }
        };
        webServer.setHandler(handler);

        try {
            webServer.start();

        } catch (Exception e) {
            Log.d(LOG_TAG, "unexpected exception starting Web server: " + e);

        }
    }

    private String getPublicInternetAddress() {
        return "127.0.0.1";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
