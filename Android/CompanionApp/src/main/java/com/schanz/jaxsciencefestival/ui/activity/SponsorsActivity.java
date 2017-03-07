package com.schanz.jaxsciencefestival.ui.activity;

import android.os.Bundle;

import com.schanz.jaxsciencefestival.R;

public class SponsorsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sponsors_activity);
    }
}
