package ntut.edu.lab1323.mitrastar.view;

import java.io.File;

public class UploadMessageEvent {
    public final File tempFile;

    public UploadMessageEvent(File tempFile){
        this.tempFile = tempFile;
    }

    public File getTempFile() {
        return tempFile;
    }
}
