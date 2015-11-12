package ctec.soundvideo;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class SoundActivity extends AppCompatActivity implements Runnable
{
    private Button playButton;
    private Button stopButton;
    private Button pauseButton;
    private Button videoButton;
    private MediaPlayer soundPlayer;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private Spinner soundSpinner;
    private ArrayList<Integer> soundList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        playButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.halloweensounds);
        soundSpinner = (Spinner) findViewById(R.id.soundSpinner);

        soundList = new ArrayList<Integer>();
        soundList.add(R.raw.halloweensounds);
        soundList.add(R.raw.halloweenmidnight);
        soundList.add(R.raw.epicjourney);

        loadSpinner();

        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();

    }

    private void loadSpinner()
    {
        ArrayAdapter<Integer> listAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, soundList);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(listAdapter);
    }

    public void setupListeners()
    {
        soundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                soundPlayer.stop();
                int selectedSoundR = Integer.parseInt(soundSpinner.getSelectedItem().toString());
                soundPlayer = MediaPlayer.create(getBaseContext(), selectedSoundR);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                soundPlayer = MediaPlayer.create(getBaseContext(), null);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.halloweensounds);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(v.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    soundPlayer.seekTo(progress);
                }
            }
        });
    }


    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal = soundPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);

        while (soundPlayer != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();
            } catch (InterruptedException soundException)
            {
                return;
            } catch (Exception otherException)
            {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }

}
