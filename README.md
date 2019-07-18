# AndroidPlayYoutubeandOtherVideos
This repository consist of an android app which plays Youtube's and other videos on YoutbePlayer and Surface View respectively.

**Note:** Generate your own **API KEY** from [here](https://console.developers.google.com/)

**Sample Urls**

    Youtube: https://www.youtube.com/watch?v=x8qTEMkZCPs

    Other: http://techslides.com/demos/sample-videos/small.mp4
    
**Step: 01(Setting YoutubePlayer Api)**

Add **Youtube Player's** Depedency in build.gradle(Module:app)

    //youtube player
    implementation files('libs/YouTubeAndroidPlayerApi.jar')
    
Adding Youtube Player Api **Jar File**

**i-** Go on [this page](https://developers.google.com/youtube/android/player/downloads/) and download YoutubePlayerApi.jar file.

**ii-** Go to Project Level under Project Window.

**iii-** Create a libs folder under app folder and paste the downloaded YoutubePlayerApi.jar file

**Step: 02(Designing UI)**

        <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <com.google.android.youtube.player.YouTubePlayerView
            android:id="@+id/videoView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="visible" />

        <SurfaceView
            android:id="@+id/surfaceView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="invisible">

        </SurfaceView>
        </RelativeLayout>
        
Set **surfaceview** invisible

**Step: 03(MainActivity.java)**

Extends your MainActivity to **YouTubeBaseActivity** and implements *SurfaceHolder.Callback and MediaPlayer.OnPreparedListener*

    public static final String YOUR_API_KEY = "AIzaSyA4B2iW8KMW2Dcp1Eh7Ak1910LmNTIEsmk";
 
initialize the followings

     private MediaPlayer mediaPlayer;
     private static final String videoURL = "https://www.youtube.com/watch?v=x8qTEMkZCPs";
     String[] separated;
   
You can paste other Url in videoURL for non-Youtube videos

Initalize **surface** and **Youtube views**

      YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        
Define **regex pattern** for validating either Url(videoURL) is of Youtube or other.

     String regexYoutUbe = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
  
Validate the **videoURL** and play vidoe if it is Youtube's link

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
Otherwise, the videoUrl is **not** of Youtube.

     else {
                try {
                    surfaceView.setVisibility(View.VISIBLE);
                    SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
                    SurfaceHolder surfaceHolder = surfaceView.getHolder();
                    surfaceHolder.addCallback(MainActivity.this);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            
Method to get Youtube's *Id*

       public static String getYoutubeId(String url) {
            String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?[^\\w\\-]|$(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
In last, method to create **Surface(non-Youtube's link)** and other methods onPause(), onDestroy()

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
    
 **OutPut**
 
 Using **Youtube's Link**
 
 ![!Youtube](https://i.ibb.co/tqbvFPX/Youtube-Link.png)
 
 Using **Other Link**
 
 ![Other Url](https://i.ibb.co/hmPJk0G/device-2019-07-18-154515.png)
