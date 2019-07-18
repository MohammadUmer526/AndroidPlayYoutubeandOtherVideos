package com.example.videoview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends YouTubeBaseActivity implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener {

    public static final String YOUR_API_KEY = "AIzaSyA4B2iW8KMW2Dcp1Eh7Ak1910LmNTIEsmk";
    private MediaPlayer mediaPlayer;
    private static final String videoURL = "https://www.youtube.com/watch?v=x8qTEMkZCPs";
    private static final String videoYoutTube2 = "https://www.youtube.com/watch?v=x8qTEMkZCP";
    private static final String videoYouTube3 = "lW7DWV2jST0";
    private static final String otherUrl1 = "http://techslides.com/demos/sample-videos/small.mp4";
    private static final String otherUrl2 = "http://techslides.com/demos/samples/sample.mp4";
    private static final String otherUrl3 = "http://techslides.com/demos/samples/sample.flv";
    private SurfaceView surfaceView;

//    String videoUrl = "https://www.youtube.com/watch?v=x8qTEMkZCPs";
    String[] separated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        String regexYoutUbe = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (videoURL.matches(regexYoutUbe)) {
            //Initialize the YouTube Player//
            surfaceView.setVisibility(View.INVISIBLE);
            youTubePlayerView.setVisibility(View.VISIBLE);

            youTubePlayerView.initialize(YOUR_API_KEY, new YouTubePlayer.OnInitializedListener() {@Override

            //If the YouTube Player is initialized successfully...//
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                //..then start playing the following video//
                separated = videoURL.split("=");

                Toast.makeText(MainActivity.this, separated[0], Toast.LENGTH_SHORT).show();
                String videoId = getYoutubeId(videoURL);
                youTubePlayer.loadVideo(videoId);
            }

                @Override

                //If the initialization fails...//
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    //...then display a toast//

                    Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            try {
                surfaceView.setVisibility(View.VISIBLE);
                SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
                SurfaceHolder surfaceHolder = surfaceView.getHolder();
                surfaceHolder.addCallback(MainActivity.this);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static String getYoutubeId(String url) {
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.setDataSource(otherUrl3);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MainActivity.this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        releasedMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasedMediaPlayer();
    }

    private void releasedMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}