package com.schanz.jaxsciencefestival.util;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.schanz.jaxsciencefestival.App;
import com.schanz.jaxsciencefestival.R;

public final class ToastHelper {
    private ToastHelper() {
    }

    public static void showBlue(String message) {
        Toast toast = Toast.makeText(App.instance(), message, Toast.LENGTH_LONG);
        ViewGroup layout = (ViewGroup) toast.getView();
        TextView label = (TextView) layout.getChildAt(0);
        label.setTextColor(ContextCompat.getColor(App.instance(), R.color.blue_atlantis));
        label.setTextSize(24);
        toast.show();
    }

    public static void showRed(String message) {
        Toast toast = Toast.makeText(App.instance(), message, Toast.LENGTH_LONG);
        ViewGroup layout = (ViewGroup) toast.getView();
        TextView label = (TextView) layout.getChildAt(0);
        label.setTextColor(ContextCompat.getColor(App.instance(), R.color.red_cinnabar));
        label.setTextSize(24);
        toast.show();
    }
}
