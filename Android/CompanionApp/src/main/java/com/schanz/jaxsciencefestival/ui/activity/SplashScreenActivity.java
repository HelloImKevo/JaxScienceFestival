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

    private static final long SPLASH_TIME = 2 * 1000;
    private AsyncTask<Void, Void, Void> mSplashScreenTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Enable Crittercism on Release Builds Only
        // Crittercism.initialize(getApplicationContext(), App.CRITTERCISM_APP_ID);
        // Crittercism.setUsername("kschanz");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_activity);

        ViewGroup splashLayout = (ViewGroup)findViewById(R.id.splash_container);
        splashLayout.setOnTouchListener(this);

        mSplashScreenTask = new AsyncTask<Void, Void, Void>() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mSplashScreenTask.cancel(true);
        v.performClick();
        endSplashScreen();
        return false;
    }

    private void endSplashScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
