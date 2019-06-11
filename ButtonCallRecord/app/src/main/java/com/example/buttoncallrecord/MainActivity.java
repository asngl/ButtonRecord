package com.example.buttoncallrecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_Record_Audio = 0;
    private Button record,play,stop;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_Record_Audio);
        }
        record = (Button)findViewById(R.id.button);
        play = (Button)findViewById(R.id.button3);
        stop = (Button)findViewById(R.id.button2);
        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/recording12.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }catch(IllegalStateException ise){
                    ise.printStackTrace();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Recording Started", Toast.LENGTH_LONG).show();
            }

        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder=null;
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(),"Audio Recorded Successfully",Toast.LENGTH_LONG).show();

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // make something
                }

            }
        });
    }

}
