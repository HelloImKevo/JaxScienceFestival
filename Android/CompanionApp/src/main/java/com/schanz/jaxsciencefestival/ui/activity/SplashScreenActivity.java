package com.schanz.jaxsciencefestival.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.schanz.jaxsciencefestival.R;

public class SplashScreenActivity extends Activity implements View.OnTouchListener {

    private static final long SPLASH_TIME = 5 * 1000;
    private static AsyncTask<Void, Void, Void> sSplashScreenTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_activity);

        ViewGroup splashLayout = (ViewGroup) findViewById(R.id.splash_container);
        splashLayout.setOnTouchListener(this);

        if (sSplashScreenTask == null) {
            sSplashScreenTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        Thread.sleep(SPLASH_TIME);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void nothing) {
                    endSplashScreen();
                }
            }.execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sSplashScreenTask != null) {
            sSplashScreenTask.cancel(true);
        }
        sSplashScreenTask = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        sSplashScreenTask.cancel(true);
        v.performClick();
        endSplashScreen();
        return false;
    }

    private void endSplashScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
