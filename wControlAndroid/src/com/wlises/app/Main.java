package com.wlises.app;

import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.os.Vibrator;
import android.view.Window;
import android.view.View;

import com.wlises.app.comm.IRHandler;

public class Main extends Activity {

    private Vibrator vibrator;

    private final long VIBRATION_TIME = 50;

    private IRHandler irHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        irHandler = new IRHandler(this);
    }

    public void button_action(View view) {

        // Send code
        irHandler.send( getResources().getResourceEntryName(view.getId()));

        // Vibrate
        vibrator.vibrate(VIBRATION_TIME);
    }

}