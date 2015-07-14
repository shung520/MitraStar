package ntut.edu.lab1323.mitrastar.service.task;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PlayAudioTask extends AsyncTask<Void, Void, Void> {
    private File file;

    public PlayAudioTask(File file) {
        this.file = file;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            FileInputStream fis = new FileInputStream(this.file);
            Log.d("UploadAudioFileHandler", "delete " + this.file.getName() + " -> " + Boolean.toString(this.file.delete()));

            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
