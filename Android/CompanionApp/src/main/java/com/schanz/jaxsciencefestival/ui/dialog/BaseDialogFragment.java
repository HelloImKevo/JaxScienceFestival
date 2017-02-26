package com.schanz.jaxsciencefestival.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.util.Logger;

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(getClass().getSimpleName(), "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        /**
         * Resolves a known memory leak common to all {@link DialogFragment}s,
         * that only occurs on specific Android versions;
         * Do not remove without thorough regression testing of dialog memory management.
         */
        if (getView() instanceof ViewGroup) {
            ((ViewGroup)getView()).removeAllViews();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //region Alert Dialogs

    protected void showAlertDialog(String title, String message, String neutralText) {
        showAlertDialog(title, message, neutralText, null);
    }

    protected void showAlertDialog(@StringRes int title, @StringRes int message, @StringRes int neutralText) {
        showAlertDialog(getString(title), getString(message), getString(neutralText), null);
    }

    protected void showAlertDialog(String title, String message, String neutralText,
                                   @Nullable View.OnClickListener neutralListener) {
        CommonAlertDialog dialog = new CommonAlertDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNeutralButton(neutralText, neutralListener);
        dialog.show();
    }

    protected void showAlertDialog(@StringRes int title, @StringRes int message, @StringRes int negativeText,
                                   View.OnClickListener negativeListener, @StringRes int positiveText, View.OnClickListener positiveListener) {
        showAlertDialog(getString(title), getString(message), getString(negativeText), negativeListener, getString(
                positiveText), positiveListener);
    }

    protected void showAlertDialog(String title, String message, String negativeText,
                                   View.OnClickListener negativeListener, String positiveText, View.OnClickListener positiveListener) {
        showAlertDialog(title, message, negativeText, negativeListener, positiveText, positiveListener,
                CommonAlertDialog.Style.STANDARD);
    }

    protected void showAlertDialog(String title, String message, String negativeText,
                                   View.OnClickListener negativeListener, String positiveText, View.OnClickListener positiveListener,
                                   @NonNull CommonAlertDialog.Style style) {
        CommonAlertDialog dialog = new CommonAlertDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setStyle(style);
        dialog.setNegativeButton(negativeText, negativeListener);
        dialog.setPositiveButton(positiveText, positiveListener);
        dialog.show();
    }

    protected void showAlertDialog(String title, String message, View.OnClickListener positiveListener) {
        CommonAlertDialog dialog = new CommonAlertDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNegativeButton(getString(R.string.common_cancel), null);
        dialog.setPositiveButton(getString(R.string.common_ok), positiveListener);
        dialog.show();
    }

    //endregion
}
