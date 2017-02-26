package com.schanz.jaxsciencefestival.presenter;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.schanz.core.util.ProLog;
import com.schanz.jaxsciencefestival.App;
import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.ai.Message;
import com.schanz.jaxsciencefestival.model.DataBlob;
import com.schanz.jaxsciencefestival.model.NewsEvent;

import icepick.State;

// TODO: Write unit tests for the presenter
// TODO: Create an App / Demo 'database' parent object to exist in memory only
public class MainPresenter extends BasePresenter<DataBlob, MainView> {

    private static final String TAG = MainPresenter.class.getSimpleName();

    @State
    AtomicBoolean loadingData;

    public MainPresenter() {
        loadingData = new AtomicBoolean(false);
    }

    @Override
    protected void updateView() {
        // Business logic is in the presenter
        if (model().size() == 0) {
            ProLog.d(TAG, "updateView :: view :: showEmpty");
            view().showEmpty();
        } else {
            ProLog.d(TAG, "updateView :: view :: showItems");
            view().showItems(model());
        }
    }

    @Override
    public void bindView(@NonNull MainView view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (modelStale() && !loadingData.get()) {
            ProLog.d(TAG, "bindView :: view :: showLoading");
            view().showLoading();
            loadData();
        }
    }

    public void recreateData(@NonNull MainView view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (modelStale() && !loadingData.get()) {
            ProLog.d(TAG, "bindView :: view :: showLoading");
            view().showLoading();
            loadData();
        }
    }

    public boolean loading() {
        return loadingData.get();
    }

    private boolean modelStale() {
        if (model() == null) {
            return true;
        }
        // TODO: Wire this up with logic to determine when a refresh is necessary
        return true;
    }

    private void loadData() {
        loadingData.set(true);
        new LoadDataTask().execute();
    }

    // It's OK for this class not to be static and to keep a reference to the Presenter, as this
    // is retained during orientation changes and is lightweight (has no activity/view reference)
    private class LoadDataTask extends AsyncTask<Void, Void, DataBlob> {
        @NonNull
        @Override
        protected DataBlob doInBackground(Void... params) {
            // Simulate this taking longer than it actually does ;)
            SystemClock.sleep(3000L);

            // Temporary database model blob that represents all application model objects
            final DataBlob dataBlob = new DataBlob();

            // Chat Companions
            List<ChatCompanion> chatCompanions = new ArrayList<>();
            ChatCompanion companion;

            companion = new ChatCompanion("Anthony", ChatCompanion.Sex.MALE, R.drawable.profile_man_1);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.GREETING, Message.Mood.NEUTRAL));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Amanda", ChatCompanion.Sex.FEMALE, R.drawable.profile_woman_1);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.RESPONSE, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Gerald", ChatCompanion.Sex.MALE, R.drawable.profile_man_2);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.STATEMENT, Message.Mood.NEUTRAL));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Claire", ChatCompanion.Sex.FEMALE, R.drawable.profile_woman_2);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.QUESTION, Message.Mood.NEGATIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Bartholomew", ChatCompanion.Sex.MALE, R.drawable.profile_man_3);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.RESPONSE, Message.Mood.NEGATIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Sarah", ChatCompanion.Sex.MALE, R.drawable.profile_woman_3);
            companion.messageHistory.addMessage(
                    companion.getMessage(App.instance(), Message.Type.GREETING, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            dataBlob.setChatCompanions(chatCompanions);

            // News Events
            List<NewsEvent> newsEvents = new ArrayList<>();
            NewsEvent event;

            event = new NewsEvent("BioTech Engineering Gaining Traction",
                    R.drawable.news_image_city_1);
            newsEvents.add(event);

            event = new NewsEvent("JAX Science Festival raises $15,000.00 in Scholarships",
                    R.drawable.news_image_city_2);
            newsEvents.add(event);

            event = new NewsEvent("MIT Offering $10,000.00 Scholarship to best Tech Exhibit",
                    R.drawable.news_image_nature_1);
            newsEvents.add(event);

            event = new NewsEvent("Student Exhibit demonstrates Van Der Waals Forces",
                    R.drawable.news_image_city_2);
            newsEvents.add(event);

            event = new NewsEvent("Potato Battery Exhibit starts a v6 car engine with 10 potatoes",
                    R.drawable.news_image_city_1);
            newsEvents.add(event);

            event = new NewsEvent("Biology majors study the fastest animals in the world",
                    R.drawable.news_image_nature_2);
            newsEvents.add(event);

            dataBlob.setNewsEvents(newsEvents);

            return dataBlob;
        }

        @Override
        protected void onPostExecute(@NonNull DataBlob dataBlob) {
            loadingData.set(false);

            setModel(dataBlob);
        }
    }
}
