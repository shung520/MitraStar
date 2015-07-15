package ntut.edu.lab1323.mitrastar.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.MediaController;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;

import ntut.edu.lab1323.mitrastar.R;
import ntut.edu.lab1323.mitrastar.service.HttpServer;

public class MainActivity extends ActionBarActivity {
    static final String LOG_TAG = "MainActivity";
    private HttpServer server;
    private ImageView testImageView;
    private VideoView testVideoView;
    private MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.testImageView = (ImageView) this.findViewById(R.id.test_image_view);
        this.testVideoView = (VideoView) this.findViewById(R.id.test_video_view);
        this.mc = new MediaController(this);
        this.testVideoView.setMediaController(this.mc);
        server = new HttpServer(this);
        server.start();

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

    public void showBitmap(Bitmap bmp) {
        if (bmp != null) {
            this.testImageView.setImageBitmap(bmp);
        }
    }

    public void showVideo(File file) {
        this.testVideoView.setVideoPath(file.getPath());
        this.testVideoView.requestFocus();
        this.testVideoView.start();
    }
}
