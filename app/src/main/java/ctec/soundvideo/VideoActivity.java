package ctec.soundvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.view.*;
import android.net.Uri;


public class VideoActivity extends AppCompatActivity
{
    private Button homeButton;
    private VideoView myPlayer;
    private MediaController myVideoController;
    private Uri videoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        homeButton = (Button) findViewById(R.id.homeButton);
        myPlayer = (VideoView) findViewById(R.id.videoView);

        videoLocation = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.funnyvideo);
        myVideoController = new MediaController(this);
        //Prepare the video
        setupMedia();
        setupListeners();
    }

    private void setupMedia()
    {
        myPlayer.setMediaController(myVideoController);
        myPlayer.setVideoURI(videoLocation);
    }

    private void setupListeners()
    {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
