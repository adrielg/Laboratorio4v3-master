package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ayuda);

        //Abre un WevView
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=d-rXvlzgqQU")));
        finish();

        //Para reproducir archivos almacenados en la memoria SDCard:
        //v.setVideoPath("/mnt/sdcard/videoEjemplo.mp4");


        /*Este Medoto ya no Funciona, usando VideoView*/
        /*final VideoView mVideoView = (VideoView) findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);

        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoPath("https://www.youtube.com/watch?v=d-rXvlzgqQU");
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }});
        */
    }
}

