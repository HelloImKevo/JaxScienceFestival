package com.schanz.jaxsciencefestival.model;

import java.util.List;

import com.schanz.jaxsciencefestival.ai.ChatCompanion;

public class DataBlob {

    private List<NewsEvent> mNewsEvents;
    private List<ChatCompanion> mChatCompanions;
    private List<QuizQuestion> mQuizQuestions;

    public DataBlob() {
    }

    public List<NewsEvent> getNewsEvents() {
        return mNewsEvents;
    }

    public void setNewsEvents(List<NewsEvent> newsEvents) {
        mNewsEvents = newsEvents;
    }

    public List<ChatCompanion> getChatCompanions() {
        return mChatCompanions;
    }

    public void setChatCompanions(List<ChatCompanion> chatCompanions) {
        mChatCompanions = chatCompanions;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return mQuizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        mQuizQuestions = quizQuestions;
    }

    public int size() {
        int size = 0;
        if (mNewsEvents != null) {
            size += mNewsEvents.size();
        }
        if (mChatCompanions != null) {
            size += mChatCompanions.size();
        }
        return size;
    }
}
