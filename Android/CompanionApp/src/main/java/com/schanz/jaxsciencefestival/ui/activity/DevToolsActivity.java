package com.schanz.jaxsciencefestival.ui.activity;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ui.view.RingIndicatorView;
import com.schanz.jaxsciencefestival.util.development.Generator;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions") // Suppress the silly NPE warnings
public class DevToolsActivity extends BaseActivity {

    @BindView(R.id.ring_indicator_view_1)
    RingIndicatorView ringIndicatorView1;
    @BindView(R.id.ring_indicator_view_2)
    RingIndicatorView ringIndicatorView2;
    @BindView(R.id.ring_indicator_view_3)
    RingIndicatorView ringIndicatorView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_tools_activity);
        ButterKnife.bind(this);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_do_magic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringIndicatorView1.setLevel(new Random().nextInt(101));
                ringIndicatorView1.setColor(Generator.getRandomColorRes());
                ringIndicatorView2.setLevel(new Random().nextInt(101));
                ringIndicatorView2.setColor(Generator.getRandomColorRes());
                ringIndicatorView3.setLevel(new Random().nextInt(101));
                ringIndicatorView3.setColor(Generator.getRandomColorRes());
            }
        });
    }
}
