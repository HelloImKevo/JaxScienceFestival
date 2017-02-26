package com.schanz.jaxsciencefestival.async;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

// <Params, Progress, Result>
public abstract class ProgressBarAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    @Nullable
    private WeakReference<ProgressBar> mProgressBarRef;

    public ProgressBarAsyncTask(ProgressBar progressBar) {
        mProgressBarRef = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar = getProgressBar();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected abstract Result doInBackground(Params... params);

    @Override
    protected abstract void onProgressUpdate(Progress... values);

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        ProgressBar progressBar = getProgressBar();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Nullable
    protected final ProgressBar getProgressBar() {
        return mProgressBarRef != null ? mProgressBarRef.get() : null;
    }
}
