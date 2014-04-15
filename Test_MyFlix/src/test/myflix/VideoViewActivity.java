package test.myflix;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
 

/* This activity implements the video player. 
 * Requires that intent has putExtra with 
 * type: String  key: "title" 
 * type: Integer key: "Position" (Position of movie playback) optional, starts at 0 otherwise */

public class VideoViewActivity extends Activity {
 
    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;
    String VideoURL;
    String MovieName;
    int playbackPosition;
    static final String PLAYBACK_POSITION = "playback_position";
    static final String VIDEO_URL = "video_url";
    static final String MOVIE_NAME = "movie_name";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.videoview_main);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        
        
        if (savedInstanceState != null) 
        {
            // Restore value of members from saved state
            VideoURL = savedInstanceState.getString(VIDEO_URL);
            MovieName = savedInstanceState.getString(MOVIE_NAME);
            playbackPosition = savedInstanceState.getInt(PLAYBACK_POSITION);
        } 
        else 
        {
        	Bundle extras = this.getIntent().getExtras();
            try
            {
            	String title = extras.getString("title");
            	VideoURL = "http://cannonmovies.us/myflix/"+ title + ".mp4";
            	VideoURL = "http://cannonmovies.us/myflix/2012.mp4";
            	VideoURL = VideoURL.replace(" ","%20");
            	MovieName = title;
            }
            catch(Exception e)
            {
            	Log.e("Bundle Retrieval Exception", e.toString());
            	VideoURL = "http://cannonmovies.us/myflix/5%20Days%20of%20War.mp4";
            	MovieName = "Default Movie";
            }
            
            try
            {
            	playbackPosition = extras.getInt("Position");
            }
            catch(Exception e)
            {
            	Log.e("Bundle Retrieval Exception", "Missing Position");
            	playbackPosition = 0;
            }
           
        }
        
       
        
        // Create a progressbar
        pDialog = new ProgressDialog(VideoViewActivity.this);
        // Set progressbar title
        pDialog.setTitle(MovieName);
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        
     // Execute StreamVideo AsyncTask
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoViewActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
 
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
 
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
        
    }
    
    @Override
    public void onPause() 
    {
        super.onPause();  // Always call the superclass method first

        //Pause video playback and save location
        videoview.pause();
        playbackPosition = videoview.getCurrentPosition();
        
    }
    
    @Override
    public void onResume() 
    {
        super.onResume();  // Always call the superclass method first
        pDialog.show();
        videoview.seekTo(playbackPosition);
        videoview.requestFocus();
        
    }
    
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current spot
        savedInstanceState.putInt(PLAYBACK_POSITION, playbackPosition);
        savedInstanceState.putString(VIDEO_URL, VideoURL);
        savedInstanceState.putString(MOVIE_NAME, MovieName);
        
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
 
}
