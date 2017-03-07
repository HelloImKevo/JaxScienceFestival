package com.schanz.jaxsciencefestival.presenter;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import com.schanz.core.util.ProLog;
import com.schanz.jaxsciencefestival.App;
import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.ai.Message;
import com.schanz.jaxsciencefestival.model.DataBlob;
import com.schanz.jaxsciencefestival.model.NewsEvent;
import com.schanz.jaxsciencefestival.model.QuizQuestion;

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
            SystemClock.sleep(1800L);

            // Temporary database model blob that represents all application model objects
            final DataBlob dataBlob = new DataBlob();

            // Chat Companions
            List<ChatCompanion> chatCompanions = new ArrayList<>();
            ChatCompanion companion;

            companion = new ChatCompanion("Lucille Cronley", ChatCompanion.Sex.FEMALE,
                    R.drawable.profile_woman_07, R.color.red_cinnabar);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.RESPONSE, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Luther Graves", ChatCompanion.Sex.MALE,
                    R.drawable.profile_man_07, R.color.green_aegean_sea);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.GREETING, Message.Mood.NEUTRAL));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Anthony Garcia", ChatCompanion.Sex.MALE,
                    R.drawable.profile_man_1, R.color.orange_california);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.GREETING, Message.Mood.NEUTRAL));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Lara Croft", ChatCompanion.Sex.FEMALE,
                    R.drawable.profile_woman_05, R.color.blue_voyager_sky);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.RESPONSE, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Jenny McArthur", ChatCompanion.Sex.FEMALE,
                    R.drawable.profile_woman_04, R.color.green_summer_grass);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.RESPONSE, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Amanda Bartlett", ChatCompanion.Sex.FEMALE,
                    R.drawable.profile_woman_1, R.color.purple_magic);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.RESPONSE, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Gerald Butler", ChatCompanion.Sex.MALE,
                    R.drawable.profile_man_2, R.color.green_planetary_halo);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.STATEMENT, Message.Mood.NEUTRAL));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Claire Hightower", ChatCompanion.Sex.FEMALE,
                    R.drawable.profile_woman_2, R.color.yellow_spoof);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.QUESTION, Message.Mood.NEGATIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Bartholomew Caulder", ChatCompanion.Sex.MALE,
                    R.drawable.profile_man_3, R.color.blue_vimeo);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.RESPONSE, Message.Mood.NEGATIVE));
            chatCompanions.add(companion);

            companion = new ChatCompanion("Sarah Roque", ChatCompanion.Sex.MALE,
                    R.drawable.profile_woman_3, R.color.red_pomegranate);
            companion.messageHistory.addMessage(companion.getMessage(
                    App.instance(), Message.Source.AI, Message.Type.GREETING, Message.Mood.POSITIVE));
            chatCompanions.add(companion);

            dataBlob.setChatCompanions(chatCompanions);

            // News Events
            List<NewsEvent> newsEvents = new ArrayList<>();
            NewsEvent event;

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_1),
                    App.instance().getString(R.string.news_story_1),
                    R.color.blue_dodger_blue,
                    R.drawable.news_math_01_thumb,
                    R.drawable.news_math_01_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_2),
                    App.instance().getString(R.string.news_story_2),
                    R.color.green_planetary_halo,
                    R.drawable.news_eng_02_thumb,
                    R.drawable.news_eng_02_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_3),
                    App.instance().getString(R.string.news_story_3),
                    R.color.blue_atlantis,
                    R.drawable.news_eng_03_thumb,
                    R.drawable.news_eng_03_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_4),
                    App.instance().getString(R.string.news_story_4),
                    R.color.orange_ecstasy,
                    R.drawable.news_eng_01_thumb,
                    R.drawable.news_eng_01_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_5),
                    App.instance().getString(R.string.news_story_1),
                    R.color.purple_honey_flower,
                    R.drawable.news_math_03_thumb,
                    R.drawable.news_math_03_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_6),
                    App.instance().getString(R.string.news_story_2),
                    R.color.yellow_polished_gold,
                    R.drawable.news_sci_01_thumb,
                    R.drawable.news_sci_01_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_7),
                    App.instance().getString(R.string.news_story_3),
                    R.color.red_cinnabar,
                    R.drawable.news_sci_02_thumb,
                    R.drawable.news_sci_02_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_8),
                    App.instance().getString(R.string.news_story_4),
                    R.color.blue_vimeo,
                    R.drawable.news_sci_03_thumb,
                    R.drawable.news_sci_03_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_9),
                    App.instance().getString(R.string.news_story_1),
                    R.color.green_summer_grass,
                    R.drawable.news_tech_01_thumb,
                    R.drawable.news_tech_01_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_10),
                    App.instance().getString(R.string.news_story_2),
                    R.color.green_summer_grass,
                    R.drawable.news_tech_02_thumb,
                    R.drawable.news_tech_02_lg);
            newsEvents.add(event);

            event = new NewsEvent(
                    App.instance().getString(R.string.news_headline_11),
                    App.instance().getString(R.string.news_story_3),
                    R.color.green_summer_grass,
                    R.drawable.news_tech_03_thumb,
                    R.drawable.news_tech_03_lg);
            newsEvents.add(event);

            dataBlob.setNewsEvents(newsEvents);

            // Quiz Questions
            List<QuizQuestion> quizQuestions = new ArrayList<>();
            QuizQuestion question;

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "What principle explains why cold food warms up and hot food cools off when stored at room temperature?",
                    "Entropy",
                    new ArrayList<String>() {{
                        add("Chemical Equilibrium");
                        add("Momentum");
                        add("Relativity");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "Which color is not considered to be one of the primary colors of light?",
                    "Yellow",
                    new ArrayList<String>() {{
                        add("Red");
                        add("Blue");
                        add("Green");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "What causes the disease toxoplasmosis?",
                    "A Protozoan",
                    new ArrayList<String>() {{
                        add("A Virus");
                        add("A Water Bear");
                        add("A Bacterium");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "Meat should be kept frozen at what temperature in degrees Fahrenheit?",
                    "0 degrees or below",
                    new ArrayList<String>() {{
                        add("Between 10 and 20 degrees");
                        add("Between 20 and 30 degrees");
                        add("Just below 32 degrees");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "Amps are a unit of measurement of what?",
                    "Electrical Current",
                    new ArrayList<String>() {{
                        add("Electric Charge");
                        add("Electric Field Strength");
                        add("Electric Potential");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.SCIENCE,
                    "Which of these animals lays eggs?",
                    "Platypus",
                    new ArrayList<String>() {{
                        add("Tasmanian Devil");
                        add("Kangaroo");
                        add("Koala");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.TECHNOLOGY,
                    "Which one of the following instruments is used to measure humidity?",
                    "Hygrometer",
                    new ArrayList<String>() {{
                        add("Anemometer");
                        add("Ammeter");
                        add("Barometer");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.TECHNOLOGY,
                    "In computer software, how many bits are in a kilobyte?",
                    "8,192",
                    new ArrayList<String>() {{
                        add("1,024");
                        add("8,000");
                        add("512");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.TECHNOLOGY,
                    "Which is a type of Electrically-Erasable Programmable Read-Only Memory?",
                    "Flash",
                    new ArrayList<String>() {{
                        add("Flange");
                        add("Fury");
                        add("FRAM");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.TECHNOLOGY,
                    "Who is largely responsible for breaking the German Enigma codes, created a test that provided a foundation for artificial intelligence?",
                    "Alan Turing",
                    new ArrayList<String>() {{
                        add("Jeff Bezos");
                        add("George Boole");
                        add("Charles Babbage");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.TECHNOLOGY,
                    "Made from a variety of materials, such as carbon, which inhibits the flow of current...?",
                    "Resistor",
                    new ArrayList<String>() {{
                        add("Choke");
                        add("Inductor");
                        add("Capacitor");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.ENGINEERING,
                    "Which two planets are most similar in size diameter wise?",
                    "Venus and Earth",
                    new ArrayList<String>() {{
                        add("Mars and Mercury");
                        add("Uranus and Neptune");
                        add("Jupiter and Saturn");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.ENGINEERING,
                    "In 1989 the Large Electron-Positron Collider went into operation and had a circumference of over 16 miles. Where is it located?",
                    "France and Switzerland",
                    new ArrayList<String>() {{
                        add("California and Nevada");
                        add("Austria and Germany");
                        add("France and Italy");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.ENGINEERING,
                    "What does a Faraday Cage shield?",
                    "Electronic Equipment",
                    new ArrayList<String>() {{
                        add("Light Gases");
                        add("Mercury");
                        add("Soft Metals");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "If a hertz is equal to one cycle per second, how many cycles per second does a megahertz equal?",
                    "1,000,000",
                    new ArrayList<String>() {{
                        add("1/1,000");
                        add("1,000");
                        add("20,000");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "In which kind of geometry is the sum of the angles inside a triangle exactly equal to 180 degrees?",
                    "Euclidean",
                    new ArrayList<String>() {{
                        add("Elliptical");
                        add("Hyperbolic");
                        add("Linear");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "What is an imaginary number?",
                    "The square root of any negative real number",
                    new ArrayList<String>() {{
                        add("Any irrational number");
                        add("Any complex number");
                        add("The result of dividing any number by zero");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "In a right-angled triangle what is the side opposite the right angle called?",
                    "Hypotenuse",
                    new ArrayList<String>() {{
                        add("Adjacent");
                        add("Congruence");
                        add("Opposite");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "In geometry, what line joins two points on a circle's circumference?",
                    "Chord",
                    new ArrayList<String>() {{
                        add("Radius");
                        add("Tangent");
                        add("Vector");
                    }});
            quizQuestions.add(question);

            question = new QuizQuestion(
                    QuizQuestion.Type.MATH,
                    "What is a number called which is divisible only by itself and one?",
                    "Prime number",
                    new ArrayList<String>() {{
                        add("Improper number");
                        add("Ordinal number");
                        add("Whole number");
                    }});
            quizQuestions.add(question);

            Collections.shuffle(quizQuestions, new Random(System.nanoTime()));
            dataBlob.setQuizQuestions(quizQuestions);

            return dataBlob;
        }

        @Override
        protected void onPostExecute(@NonNull DataBlob dataBlob) {
            loadingData.set(false);

            setModel(dataBlob);
        }
    }
}
