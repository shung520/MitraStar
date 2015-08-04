package ntut.edu.lab1323.mitrastar.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.CaptioningManager;
import android.widget.Button;
import android.widget.MediaController;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.VideoSurfaceView;
import com.google.android.exoplayer.metadata.TxxxMetadata;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.SubtitleView;
import com.google.android.exoplayer.util.Util;

import java.io.File;
import java.util.Map;

import de.greenrobot.event.EventBus;
import ntut.edu.lab1323.mitrastar.R;
import ntut.edu.lab1323.mitrastar.service.HttpServer;
import ntut.edu.lab1323.mitrastar.service.socket.UDPClient;
import ntut.edu.lab1323.mitrastar.view.component.MagicFileChooser;
import ntut.edu.lab1323.mitrastar.view.component.player.DashRendererBuilder;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoPlayer;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoPlayer.RendererBuilder;
import ntut.edu.lab1323.mitrastar.view.component.player.DemoUtil;
import ntut.edu.lab1323.mitrastar.view.component.player.UnsupportedDrmException;
import ntut.edu.lab1323.mitrastar.view.component.player.WidevineTestMediaDrmCallback;
import ntut.edu.lab1323.mitrastar.view.event.UploadMessageEvent;

public class MainActivity extends Activity implements SurfaceHolder.Callback, DemoPlayer.Listener, DemoPlayer.TextListener, DemoPlayer.Id3MetadataListener {
    static final String LOG_TAG = "MainActivity";
    private static final float CAPTION_LINE_HEIGHT_RATIO = 0.0533f;

    private HttpServer server;
    private MagicFileChooser chooser;
    private DemoPlayer player;
    private MediaController mediaController;
    private boolean playerNeedsPrepare;
    private VideoSurfaceView surfaceView;
    private SubtitleView subtitleView;
    private Button chooseFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.initVideoView();
        this.initLocalServer();
        this.initChooser();
        UDPClient udpClient = new UDPClient("192.168.1.102",8000);
        udpClient.sendMessage("keming hi!");

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.configureSubtitleView();
        if (this.player != null) {
            this.player.setBackgrounded(false);
        }
    }

    private void initChooser() {
        this.chooser = new MagicFileChooser(this);
        this.chooseFileButton = (Button) findViewById(R.id.choose_file_button);
        this.chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showFileChooser();
            }
        });
    }
    private void showFileChooser() {
        this.chooser.showFileChooser("*/*", null, true);
    }

    private void initLocalServer() {
        this.server = new HttpServer(this);
        this.server.start();
    }

    private void initVideoView() {
        View root = this.findViewById(R.id.root);
        this.mediaController = new MediaController(this);
        this.mediaController.setAnchorView(root);

        this.surfaceView = (VideoSurfaceView) this.findViewById(R.id.surface_view);
        this.surfaceView.getHolder().addCallback(this);
        this.subtitleView = (SubtitleView) this.findViewById(R.id.subtitles);

        String url = "http://140.124.182.80/dashcontent/qp20/bigbuckbunnyanimation15fps/_video/240p0kbit/bigbuckbunnyanimation15fps_0kbit_dash.mpd";
        this.player = new DemoPlayer(this.getRendererBuilder("test", Uri.parse(url)));
        this.player.addListener(this);
        this.player.setTextListener(this);
        this.player.setMetadataListener(this);
        this.playerNeedsPrepare = true;
        this.mediaController.setMediaPlayer(player.getPlayerControl());
        this.mediaController.setEnabled(true);

        if (this.playerNeedsPrepare) {
            this.player.prepare();
            this.playerNeedsPrepare = false;
        }

        this.player.setSurface(surfaceView.getHolder().getSurface());
        this.player.setPlayWhenReady(true);
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

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return captioningManager.getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    private float getCaptionFontSize() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return Math.max(getResources().getDimension(R.dimen.subtitle_minimum_font_size),
                CAPTION_LINE_HEIGHT_RATIO * Math.min(displaySize.x, displaySize.y));
    }

    private void configureSubtitleView() {
        CaptionStyleCompat captionStyle;
        float captionTextSize = getCaptionFontSize();
        if (Util.SDK_INT >= 19) {
            captionStyle = getUserCaptionStyleV19();
            captionTextSize *= getUserCaptionFontScaleV19();
        } else {
            captionStyle = CaptionStyleCompat.DEFAULT;
        }
        this.subtitleView.setStyle(captionStyle);
        this.subtitleView.setTextSize(captionTextSize);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(UploadMessageEvent event) {
        Log.d(LOG_TAG, "onEvent");

        File file = event.getTempFile();

        Log.d(LOG_TAG, "delete " + file.getName() + " -> " + Boolean.toString(file.delete()));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (this.player != null) {
            this.player.setSurface(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.player != null) {
            this.player.blockingClearSurface();
        }
    }

    private void showControls() {
        this.mediaController.show(0);
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            showControls();
        }
    }

    @Override
    public void onError(Exception e) {
        if (e instanceof UnsupportedDrmException) {
            e.printStackTrace();
        }
        this.playerNeedsPrepare = true;
        this.showControls();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthAspectRatio) {
        this.surfaceView.setVideoWidthHeightRatio(height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }

    @Override
    public void onText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.subtitleView.setVisibility(View.INVISIBLE);
        } else {
            this.subtitleView.setVisibility(View.VISIBLE);
            this.subtitleView.setText(text);
        }
    }

    @Override
    public void onId3Metadata(Map<String, Object> metadata) {
        for (int i = 0; i < metadata.size(); i++) {
            if (metadata.containsKey(TxxxMetadata.TYPE)) {
                TxxxMetadata txxxMetadata = (TxxxMetadata) metadata.get(TxxxMetadata.TYPE);
                Log.i(LOG_TAG, String.format("ID3 TimedMetadata: description=%s, value=%s", txxxMetadata.description, txxxMetadata.value));
            }
        }
    }
}
