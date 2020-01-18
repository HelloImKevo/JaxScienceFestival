package com.schanz.jaxsciencefestival.manager;

import android.support.annotation.NonNull;

import java.util.List;

import com.schanz.jaxsciencefestival.model.QuizQuestion;

public class QuizManager {

    private static QuizManager sInstance;

    private List<QuizQuestion> quizQuestions;

    private QuizManager() {
    }

    @NonNull
    public static QuizManager instance() {
        if (sInstance == null) {
            synchronized (QuizManager.class) {
                if (sInstance == null) {
                    sInstance = new QuizManager();
                }
            }
        }
        return sInstance;
    }

    @NonNull
    public QuizQuestion.Type getType() {
        if (quizQuestions == null || quizQuestions.isEmpty()) {
            return QuizQuestion.Type.SCIENCE;
        }
        return quizQuestions.get(0).type;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
}
