package com.schanz.jaxsciencefestival.ui.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.manager.QuizManager;
import com.schanz.jaxsciencefestival.model.QuizQuestion;
import com.schanz.jaxsciencefestival.util.Logger;
import com.schanz.jaxsciencefestival.util.ToastHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static final String TAG = QuizActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_nav_view)
    NavigationView drawerNavView;

    @BindView(R.id.header_color)
    View headerColor;
    @BindView(R.id.ring_color)
    ImageView ringColor;
    @BindView(R.id.image_icon)
    ImageView imageIcon;

    @BindView(R.id.lbl_category)
    TextView lblCategory;
    @BindView(R.id.lbl_question)
    TextView lblQuestion;

    @BindView(R.id.answer_a)
    ViewGroup answerA;
    @BindView(R.id.answer_b)
    ViewGroup answerB;
    @BindView(R.id.answer_c)
    ViewGroup answerC;
    @BindView(R.id.answer_d)
    ViewGroup answerD;

    @BindView(R.id.answer_a_text)
    TextView answerAText;
    @BindView(R.id.answer_b_text)
    TextView answerBText;
    @BindView(R.id.answer_c_text)
    TextView answerCText;
    @BindView(R.id.answer_d_text)
    TextView answerDText;

    private int mQuizIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quiz_activity);

        ButterKnife.bind(this);

        mQuizIndex = 0;

        initComponents();
    }


    @Override
    protected void onResume() {
        super.onResume();
        syncUi();
    }

    private void syncUi() {
        List<QuizQuestion> questions = QuizManager.instance().getQuizQuestions();
        if (questions.isEmpty()) {
            Logger.w(TAG, "syncUi :: questions is empty!");
            return;
        }
        if (mQuizIndex >= questions.size()) {
            mQuizIndex = 0;
        }
        Logger.i(TAG, "syncUi :: quiz index = " + mQuizIndex);
        QuizQuestion question = getCurrent();
        List<String> answers = question.getPossibleAnswers();
        if (answers.size() < 4) {
            Logger.w(TAG, "syncUi :: there are not enough answers! " + answers.size());
            return;
        }
        @ColorInt int color;
        switch (question.type) {
            case SCIENCE:
                color = ContextCompat.getColor(this, R.color.green_aegean_sea);
                lblCategory.setText("Science Question");
                lblCategory.setTextColor(color);
                headerColor.setBackgroundColor(color);
                ringColor.setColorFilter(color);
                imageIcon.setColorFilter(color);
                imageIcon.setImageResource(R.drawable.ic_science_flask_white_24dp);
                break;
            case TECHNOLOGY:
                color = ContextCompat.getColor(this, R.color.blue_atlantis);
                lblCategory.setText("Technology Question");
                lblCategory.setTextColor(color);
                headerColor.setBackgroundColor(color);
                ringColor.setColorFilter(color);
                imageIcon.setColorFilter(color);
                imageIcon.setImageResource(R.drawable.ic_tech_microchip_white_24dp);
                break;
            case ENGINEERING:
                color = ContextCompat.getColor(this, R.color.purple_universe);
                lblCategory.setText("Engineering Question");
                lblCategory.setTextColor(color);
                headerColor.setBackgroundColor(color);
                ringColor.setColorFilter(color);
                imageIcon.setColorFilter(color);
                imageIcon.setImageResource(R.drawable.ic_engineering_gears_white_24dp);
                break;
            case MATH:
                color = ContextCompat.getColor(this, R.color.yellow_polished_gold);
                lblCategory.setText("Math Question");
                lblCategory.setTextColor(color);
                headerColor.setBackgroundColor(color);
                ringColor.setColorFilter(color);
                imageIcon.setColorFilter(color);
                imageIcon.setImageResource(R.drawable.ic_math_operators_white_24dp);
                break;
        }
        lblQuestion.setText(question.questionText);
        answerAText.setText(question.getPossibleAnswers().get(0));
        answerBText.setText(question.getPossibleAnswers().get(1));
        answerCText.setText(question.getPossibleAnswers().get(2));
        answerDText.setText(question.getPossibleAnswers().get(3));
    }

    private QuizQuestion getCurrent() {
        List<QuizQuestion> questions = QuizManager.instance().getQuizQuestions();
        if (questions.isEmpty()) {
            Logger.w(TAG, "syncUi :: questions is empty!");
        }
        return questions.get(mQuizIndex);
    }

    @Override
    public void onClick(View v) {
        QuizQuestion question = getCurrent();

        if (v == answerA) {
            onUserAnswer(question, answerAText);
        } else if (v == answerB) {
            onUserAnswer(question, answerBText);
        } else if (v == answerC) {
            onUserAnswer(question, answerCText);
        } else if (v == answerD) {
            onUserAnswer(question, answerDText);
        }
    }

    private void onUserAnswer(QuizQuestion question, TextView textView) {
        if (question.isCorrect(textView.getText().toString())) {
            ToastHelper.showBlue("You got it right!");
        } else {
            ToastHelper.showRed("Wrong answer, but practice makes perfect!");
        }
        mQuizIndex++;
        syncUi();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initComponents() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        drawerNavView.setNavigationItemSelectedListener(this);
        // mProgressDialog = new ProgressDialog(this);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
    }
}
