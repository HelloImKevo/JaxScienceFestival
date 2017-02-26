package com.schanz.jaxsciencefestival.ai;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import java.util.Random;

import com.schanz.jaxsciencefestival.R;

public class ChatCompanion extends AiCompanion {

    public enum Sex {
        MALE, FEMALE
    }

    @NonNull
    public final String name;
    @NonNull
    public final Sex sex;
    @DrawableRes
    public final int profilePicture;
    @NonNull
    public final MessageHistory messageHistory;

    @NonNull
    private final Random mRandom;

    public ChatCompanion(
            @NonNull String name,
            @NonNull Sex sex,
            @DrawableRes int profilePicture) {
        this.name = name;
        this.sex = sex;
        this.profilePicture = profilePicture;
        this.messageHistory = new MessageHistory();
        mRandom = new Random();
    }


    @UiThread
    @Override
    public void onUserResponse(@NonNull Message message) {
        messageHistory.addMessage(message);
    }

    @NonNull
    @Override
    public Message getMessage(
            @NonNull Context c,
            @NonNull Message.Type type,
            @NonNull Message.Mood mood) {
        return new Message(Message.Source.AI, type, mood, getAiMessage(c, type, mood));
    }

    @NonNull
    public String getAiMessage(
            @NonNull Context c,
            @NonNull Message.Type type,
            @NonNull Message.Mood mood) {
        switch (type) {
            case QUESTION:
                switch (mood) {
                    case POSITIVE:
                        return getRandomString(c, R.array.question_positive_messages);
                    case NEGATIVE:
                        return getRandomString(c, R.array.question_negative_messages);
                    case NEUTRAL:
                    default:
                        return getRandomString(c, R.array.question_neutral_messages);
                }

            case RESPONSE:
                switch (mood) {
                    case POSITIVE:
                        return getRandomString(c, R.array.response_positive_messages);
                    case NEGATIVE:
                        return getRandomString(c, R.array.response_negative_messages);
                    case NEUTRAL:
                    default:
                        return getRandomString(c, R.array.response_neutral_messages);
                }

            case GREETING:
                switch (mood) {
                    case POSITIVE:
                        return getRandomString(c, R.array.greeting_positive_messages);
                    case NEGATIVE:
                        return getRandomString(c, R.array.greeting_negative_messages);
                    case NEUTRAL:
                    default:
                        return getRandomString(c, R.array.greeting_neutral_messages);
                }

            case STATEMENT:
            default:
                switch (mood) {
                    case POSITIVE:
                        return getRandomString(c, R.array.statement_positive_messages);
                    case NEGATIVE:
                        return getRandomString(c, R.array.statement_negative_messages);
                    case NEUTRAL:
                    default:
                        return getRandomString(c, R.array.statement_neutral_messages);
                }
        }
    }

    private String getRandomString(@NonNull Context c, @ArrayRes int stringRes) {
        String[] strings = c.getResources().getStringArray(stringRes);
        return strings[mRandom.nextInt(strings.length)];
    }
}
