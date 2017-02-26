package com.schanz.jaxsciencefestival.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.schanz.jaxsciencefestival.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressDialog extends Dialog {

    @BindView(R.id.lbl_message)
    TextView lblMessage;

    final Timer timer = new Timer();
    final Handler handler = new Handler();

    public ProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);

        ButterKnife.bind(this, this);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lblMessage.setText(getRandomMessage());
                    }
                });
            }
        }, 0, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    private String getRandomMessage() {
        String[] messages = getContext().getResources().getStringArray(R.array.progress_dialog_messages);
        return messages[new Random().nextInt(messages.length)];
    }
}
