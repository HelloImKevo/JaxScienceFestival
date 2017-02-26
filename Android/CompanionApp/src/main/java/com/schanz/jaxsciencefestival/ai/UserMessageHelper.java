package com.schanz.jaxsciencefestival.ai;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import com.schanz.jaxsciencefestival.R;

public final class UserMessageHelper {

    private UserMessageHelper() {
    }

    @NonNull
    public static Message.Type getGuessedType(@NonNull Context c, @NonNull String message) {
        if (contains(c.getResources().getStringArray(R.array.user_greeting_strings), message)) {
            return Message.Type.GREETING;
        } else if (contains(c.getResources().getStringArray(R.array.user_question_strings), message)) {
            return Message.Type.QUESTION;
        }
        return Message.Type.STATEMENT;
    }

    @NonNull
    public static Message.Mood getGuessedMood(@NonNull Context c, @NonNull String message) {
        if (contains(c.getResources().getStringArray(R.array.user_positive_strings), message)) {
            return Message.Mood.POSITIVE;
        } else if (contains(c.getResources().getStringArray(R.array.user_negative_strings), message)) {
            return Message.Mood.NEGATIVE;
        }
        return Message.Mood.NEUTRAL;
    }

    private static boolean contains(String[] stringArray, String match) {
        if (stringArray != null && match != null) {
            final String matchLower = match.toLowerCase(Locale.getDefault());
            for (String string : stringArray) {
                if (matchLower.contains(string.toLowerCase(Locale.getDefault()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
