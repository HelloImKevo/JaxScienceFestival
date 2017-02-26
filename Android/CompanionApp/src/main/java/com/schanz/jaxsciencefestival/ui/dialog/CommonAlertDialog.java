package com.schanz.jaxsciencefestival.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.schanz.jaxsciencefestival.R;

public class CommonAlertDialog extends Dialog {
    public enum Style {
        /**
         * Negative and Neutral buttons are gray, the Positive button is blue.
         */
        STANDARD,

        /**
         * All buttons are blue.
         */
        ALL_BLUE_BUTTONS
    }

    private Button mBtnNegative;
    private Button mBtnPositive;
    private Button mBtnNeutral;

    @NonNull
    private Style mStyle;
    private String mTitle, mMessage, mNegative, mPositive, mNeutral;
    private View.OnClickListener mNeutralListener, mNegativeListener, mPositiveListener;

    public CommonAlertDialog(Context context) {
        super(context, 0);
        mStyle = Style.STANDARD;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_alert_dialog);

        initComponents();
        configureButtons();

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setTitle(@NonNull CharSequence title) {
        mTitle = title.toString();
    }

    public void setMessage(@NonNull CharSequence message) {
        mMessage = message.toString();
    }

    public void setStyle(@NonNull Style style) {
        mStyle = style;
    }

    public void setNeutralButton(String buttonText, View.OnClickListener neutralListener) {
        mNeutral = buttonText;
        mNeutralListener = neutralListener;
    }

    public void setNegativeButton(String buttonText, View.OnClickListener negativeListener) {
        mNegative = buttonText;
        mNegativeListener = negativeListener;
    }

    public void setPositiveButton(String buttonText, View.OnClickListener positiveListener) {
        mPositive = buttonText;
        mPositiveListener = positiveListener;
    }

    private void initComponents() {
        TextView lblTitle = (TextView)findViewById(R.id.label_title);
        lblTitle.setText(mTitle);
        TextView lblMessage = (TextView)findViewById(R.id.label_message);
        lblMessage.setText(mMessage);

        mBtnNeutral = (Button)findViewById(R.id.btn_neutral);
        mBtnNegative = (Button)findViewById(R.id.btn_negative);
        mBtnPositive = (Button)findViewById(R.id.btn_positive);
    }

    private void configureButtons() {
        switch (mStyle) {
            case ALL_BLUE_BUTTONS:
                mBtnNeutral.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_blue_states));
                mBtnNegative.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_blue_states));
                break;

            case STANDARD:
            default:
                break;
        }
        if (!TextUtils.isEmpty(mNeutral)) {
            mBtnNeutral.setVisibility(View.VISIBLE);
            mBtnNegative.setVisibility(View.GONE);
            mBtnPositive.setVisibility(View.GONE);

            mBtnNeutral.setText(mNeutral);
            mBtnNeutral.setOnClickListener(new DismissAndFireListener(mNeutralListener));
        } else {
            mBtnNeutral.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(mNegative)) {
                mBtnNegative.setText(mNegative);
                mBtnNegative.setOnClickListener(new DismissAndFireListener(mNegativeListener));
            } else {
                mBtnNegative.setVisibility(View.GONE);
            }
            mBtnPositive.setText(mPositive);
            mBtnPositive.setOnClickListener(new DismissAndFireListener(mPositiveListener));
        }
    }

    private class DismissAndFireListener implements View.OnClickListener {
        private View.OnClickListener mListener;

        public DismissAndFireListener(@Nullable View.OnClickListener clickListener) {
            mListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            dismiss();
            if (mListener != null) {
                mListener.onClick(view);
            }
        }
    }
}
