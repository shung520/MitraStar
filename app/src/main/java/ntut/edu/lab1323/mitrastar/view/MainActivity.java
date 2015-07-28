package ntut.edu.lab1323.mitrastar.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

import de.greenrobot.event.EventBus;
import ntut.edu.lab1323.mitrastar.R;
import ntut.edu.lab1323.mitrastar.service.HttpServer;
import ntut.edu.lab1323.mitrastar.view.component.MagicFileChooser;
import ntut.edu.lab1323.mitrastar.view.component.player.DashRendererBuilder;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoPlayer;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoPlayer.RendererBuilder;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoUtil;
import ntut.edu.lab1323.mitrastar.view.component.player.WidevineTestMediaDrmCallback;
import ntut.edu.lab1323.mitrastar.view.event.UploadMessageEvent;

public class MainActivity extends Activity {
    static final String LOG_TAG = "MainActivity";

    private VideoView videoView;

    private HttpServer server;
    private MagicFileChooser chooser;
    private DemoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.initVideoView();
        this.initLocalServer();
        this.initChooser();
    }

    private void initChooser() {
        this.chooser = new MagicFileChooser(this);
    }

    private void showFileChooser() {
        this.chooser.showFileChooser("*/*", null, true);
    }

    private void initLocalServer() {
        this.server = new HttpServer(this);
        this.server.start();
    }

    private void initVideoView() {
        String url = "140.124.182.80/player/dash130/index2.html?url=http://140.124.182.80/dashcontent/qp20/bigbuckbunnyanimation15fps/_video/240p0kbit/bigbuckbunnyanimation15fps_0kbit_dash.mpd";
        this.player = new DemoPlayer(this.getRendererBuilder("test", Uri.parse(url)));

        MediaController mediaController = new MediaController(this);
        this.videoView = (VideoView) this.findViewById(R.id.video_view);
        this.videoView.setMediaController(mediaController);
    }

    private RendererBuilder getRendererBuilder(String contentId, Uri contentUri) {
        String userAgent = DemoUtil.getUserAgent(this);
        return new DashRendererBuilder(userAgent, contentUri.toString(), contentId,
                new WidevineTestMediaDrmCallback(contentId));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.chooser.onActivityResult(requestCode, resultCode, data)) {
            for (File file : this.chooser.getChosenFiles()) {
                Log.e(LOG_TAG, file.getPath());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(UploadMessageEvent event) {
        Log.d(LOG_TAG, "onEvent");

        File file = event.getTempFile();
        this.videoView.setVideoPath(file.getPath());
        this.videoView.requestFocus();
        this.videoView.start();

        Log.d(LOG_TAG, "delete " + file.getName() + " -> " + Boolean.toString(file.delete()));
    }
}
