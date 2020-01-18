package com.schanz.jaxsciencefestival.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizQuestion {

    public enum Type {
        SCIENCE, TECHNOLOGY, ENGINEERING, MATH
    }

    @NonNull
    public final Type type;
    @NonNull
    public final String questionText;
    @NonNull
    public final String correctAnswer;
    @NonNull
    private final List<String> possibleAnswers;

    public QuizQuestion(
            @NonNull final Type type,
            @NonNull final String questionText,
            @NonNull final String correctAnswer,
            @NonNull final List<String> wrongAnswers) {
        this.type = type;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = new ArrayList<>();
        this.possibleAnswers.addAll(wrongAnswers);
        this.possibleAnswers.add(correctAnswer);
        Collections.shuffle(this.possibleAnswers, new Random(System.nanoTime()));
    }

    public boolean isCorrect(@NonNull String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    @NonNull
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    @NonNull
    public static List<QuizQuestion> getByType(@NonNull Type type, @NonNull List<QuizQuestion> quizQuestions) {
        final List<QuizQuestion> byType = new ArrayList<>();
        for (QuizQuestion quizQuestion : quizQuestions) {
            if (quizQuestion.type == type) {
                byType.add(quizQuestion);
            }
        }
        return byType;
    }
}
