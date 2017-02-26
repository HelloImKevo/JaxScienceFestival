package com.schanz.jaxsciencefestival.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ui.dialog.CommonAlertDialog;

import icepick.Icepick;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected final boolean isTabletConfiguration() {
        return getResources().getBoolean(R.bool.tablet_configuration);
    }

    //region Alert Dialogs

    protected void showAlertDialog(@StringRes int title, @StringRes int message, @StringRes int neutralText) {
        showAlertDialog(getString(title), getString(message), getString(neutralText));
    }

    protected void showAlertDialog(@StringRes int title, @NonNull String message, @StringRes int neutralText) {
        showAlertDialog(getString(title), message, getString(neutralText));
    }

    protected void showAlertDialog(String title, String message, String neutralText) {
        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNeutralButton(neutralText, null);
        dialog.show();
    }

    protected void showAlertDialog(
            @StringRes int title, @StringRes int message,
            @StringRes int negativeText, View.OnClickListener negativeListener,
            @StringRes int positiveText, View.OnClickListener positiveListener) {

        showAlertDialog(getString(title), getString(message), getString(negativeText), negativeListener,
                getString(positiveText), positiveListener);
    }

    protected void showAlertDialog(
            String title, String message,
            String negativeText, View.OnClickListener negativeListener,
            String positiveText, View.OnClickListener positiveListener) {

        showAlertDialog(title, message, negativeText, negativeListener, positiveText, positiveListener,
                CommonAlertDialog.Style.STANDARD);
    }

    protected void showAlertDialog(
            String title, String message, String negativeText,
            View.OnClickListener negativeListener, String positiveText, View.OnClickListener positiveListener,
            @NonNull CommonAlertDialog.Style style) {

        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setStyle(style);
        dialog.setNegativeButton(negativeText, negativeListener);
        dialog.setPositiveButton(positiveText, positiveListener);
        dialog.show();
    }

    protected void showAlertDialog(String title, String message, View.OnClickListener positiveListener) {
        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNegativeButton(getString(R.string.common_cancel), null);
        dialog.setPositiveButton(getString(R.string.common_ok), positiveListener);
        dialog.show();
    }

    //endregion

    /**
     * Shows the {@link Fragment} using {@link #getSupportFragmentManager()}.
     *
     * @param containerId The container show the fragment in.
     * @param fragment    The fragment to replace any content in the container.
     * @param tag         The fragment's tag.
     */
    protected void showFragment(@IdRes int containerId, @NonNull Fragment fragment, String tag) {
        if (isFinishing()) return;
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    /**
     * Removes the {@link Fragment} using {@link #getSupportFragmentManager()}.
     *
     * @param fragment The {@link Fragment} to remove.
     */
    protected void removeFragment(@Nullable Fragment fragment) {
        if (fragment == null || isFinishing()) return;
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }
}
