package com.schanz.core.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The better way to log. This is a wrapper class for {@link Log} that contains
 * additional methods for more flexibility and control when logging.
 */
public class ProLog {

    /** The maximum number of characters that can be logged in a single LogCat message. */
    private static final int MAX_MESSAGE_LENGTH = 4000;

    private static boolean sIsEnabled = true;
    private static boolean sUseDividers = false;
    private static boolean sWrapLongLogs = true;
    private static String sLogTag = ProLog.class.getSimpleName();

    protected ProLog() {
    }

    /**
     * Set to true to enable logs, false to disable all logging.
     */
    public static void setEnabled(boolean isEnabled) {
        sIsEnabled = isEnabled;
    }

    /**
     * Set to true to use dividers between log messages.
     */
    public static void setUseDividers(boolean useDividers) {
        sUseDividers = useDividers;
    }

    /**
     * Set to true to split long log messages into multiple messages. This is useful because the
     * LogCat buffer is limited to 1024 bytes.
     */
    public static void setWrapLongLines(boolean wrapLongLines) {
        sWrapLongLogs = wrapLongLines;
    }

    /**
     * Set the default log tag to use when one is not specified.
     */
    public static void setDefaultTag(String defaultLogTag) {
        sLogTag = defaultLogTag;
    }

    /**
     * Sends a log message of the specified logType.
     */
    private static void outputLog(int logType, @NonNull String tag, @NonNull String message) {
        switch (logType) {
            case Log.VERBOSE:
                Log.v(tag, message);
                break;

            case Log.DEBUG:
                Log.d(tag, message);
                break;

            case Log.INFO:
                Log.i(tag, message);
                break;

            case Log.WARN:
                Log.w(tag, message);
                break;

            case Log.ERROR:
                Log.e(tag, message);
                break;

            case Log.ASSERT:
                Log.wtf(tag, message);
                break;
        }
    }

    /**
     * Sends a log message of the specified logType, wrapping messages that are over
     * {@value #MAX_MESSAGE_LENGTH} characters.
     */
    private static void outputLogWithWrapping(int logType, @NonNull String tag,
            @NonNull String message) {
        int index = 0;
        while (message.length() > index + MAX_MESSAGE_LENGTH) {
            outputLog(logType, tag, message.substring(index, (index + MAX_MESSAGE_LENGTH)));
            index += MAX_MESSAGE_LENGTH;
        }

        outputLog(logType, tag, message.substring(index));
    }

    /**
     * Sends a log message with any formatting that is enabled.
     */
    private static void print(int logType, @NonNull String tag, @Nullable String message) {
        if (!sIsEnabled) {
            return;
        }

        if (sUseDividers) {
            printDivider();
        }

        if (message == null) {
            outputLog(logType, tag, "null");
        } else if (sWrapLongLogs) {
            outputLogWithWrapping(logType, tag, message);
        } else {
            outputLog(logType, tag, message);
        }
    }

    /**
     * Prints a divider {@link Log#DEBUG} log.
     */
    public static void printDivider() {
        char[] fill = new char[80];
        Arrays.fill(fill, '-');
        String divider = new String(fill);
        Log.d(sLogTag, divider);
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param message The message you would like logged.
     */
    public static void v(String message) {
        v(sLogTag, message);
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param throwable An exception to log.
     */
    public static void v(Throwable throwable) {
        v(sLogTag, throwable);
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void v(String tag, String message) {
        print(Log.VERBOSE, tag, message);
    }

    /**
     * Send a {@link Log#VERBOSE} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param throwable An exception to log.
     */
    public static void v(String tag, Throwable throwable) {
        v(tag, Log.getStackTraceString(throwable));
    }

    /**
     * Send a {@link Log#VERBOSE} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    public static void v(String tag, String message, Throwable throwable) {
        v(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param message The message you would like logged.
     */
    public static void d(String message) {
        d(sLogTag, message);
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param throwable An exception to log.
     */
    public static void d(Throwable throwable) {
        d(sLogTag, throwable);
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void d(String tag, String message) {
        print(Log.DEBUG, tag, message);
    }

    /**
     * Send a {@link Log#DEBUG} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param throwable An exception to log.
     */
    public static void d(String tag, Throwable throwable) {
        d(tag, Log.getStackTraceString(throwable));
    }

    /**
     * Send a {@link Log#DEBUG} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    public static void d(String tag, String message, Throwable throwable) {
        d(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param message The message you would like logged.
     */
    public static void i(String message) {
        i(sLogTag, message);
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param throwable An exception to log.
     */
    public static void i(Throwable throwable) {
        i(sLogTag, throwable);
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void i(String tag, String message) {
        print(Log.INFO, tag, message);
    }

    /**
     * Send an {@link Log#INFO} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param throwable An exception to log.
     */
    public static void i(String tag, Throwable throwable) {
        i(tag, Log.getStackTraceString(throwable));
    }

    /**
     * Send an {@link Log#INFO} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    public static void i(String tag, String message, Throwable throwable) {
        i(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param message The message you would like logged.
     */
    public static void w(String message) {
        w(sLogTag, message);
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param throwable An exception to log.
     */
    public static void w(Throwable throwable) {
        w(sLogTag, throwable);
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void w(String tag, String message) {
        print(Log.WARN, tag, message);
    }

    /**
     * Send a {@link Log#WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param throwable An exception to log.
     */
    public static void w(String tag, Throwable throwable) {
        w(tag, Log.getStackTraceString(throwable));
    }

    /**
     * Send a {@link Log#WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    public static void w(String tag, String message, Throwable throwable) {
        w(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param message The message you would like logged.
     */
    public static void e(String message) {
        e(sLogTag, message);
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param throwable An exception to log.
     */
    public static void e(Throwable throwable) {
        e(sLogTag, throwable);
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void e(String tag, String message) {
        print(Log.ERROR, tag, message);
    }

    /**
     * Send an {@link Log#ERROR} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param throwable An exception to log.
     */
    public static void e(String tag, Throwable throwable) {
        System.out.println("TEST-- " + throwable);
        e(tag, Log.getStackTraceString(throwable));
    }

    /**
     * Send an {@link Log#ERROR} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     * activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    public static void e(String tag, String message, Throwable throwable) {
        e(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The error will always
     * be logged at level ASSERT with the call stack. Depending on system configuration, a report
     * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void wtf(String tag, String message) {
        print(Log.ASSERT, tag, message);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen. Similar to
     * {@link Log#wtf(String, String)}, with an exception to log.
     *
     * @param tag Used to identify the source of a log message.
     * @param throwable An exception to log.
     */
    public static void wtf(String tag, Throwable throwable) {
        wtf(tag, throwable.getMessage(), throwable);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen. Similar to
     * {@link Log#wtf(String, Throwable)}, with a message as well.
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log. May be null.
     */
    public static void wtf(String tag, String message, Throwable throwable) {
        wtf(tag, message + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Dump the contents of a variable at a {@link Log#DEBUG} log level. Supported variable types:
     * <ul>
     * <li>Primitive array: shows all values</li>
     * <li>{@link Object} array: shows all values</li>
     * <li>{@link JSONArray}: shows all values</li>
     * <li>{@link Iterable}: shows all values</li>
     * <li>{@link Map}: shows all keys with their values</li>
     * <li>{@link JSONObject}: shows all keys with their values</li>
     * <li>{@link Intent}: shows all extras in {@link Intent#getExtras()}</li>
     * <li>{@link Bundle}: shows all extras</li>
     * </ul>
     * All other objects will simply log {@link Object#toString()}.
     *
     * @param object An object to log. May be null.
     */
    public static void dump(Object object) {
        dump(sLogTag, object);
    }

    /**
     * Dump the contents of a variable at a {@link Log#DEBUG} log level. Supported variable types:
     * <ul>
     * <li>Primitive array: shows all values</li>
     * <li>{@link Object} array: shows all values</li>
     * <li>{@link JSONArray}: shows all values</li>
     * <li>{@link Iterable}: shows all values</li>
     * <li>{@link Map}: shows all keys with their values</li>
     * <li>{@link JSONObject}: shows all keys with their values</li>
     * <li>{@link Intent}: shows all extras in {@link Intent#getExtras()}</li>
     * <li>{@link Bundle}: shows all extras</li>
     * </ul>
     * All other objects will simply log {@link Object#toString()}.
     *
     * @param tag Used to identify the source of a log message.
     * @param object An object to log. May be null.
     */
    public static void dump(String tag, Object object) {
        dump(tag, object, null, 0);
    }

    /**
     * Dump the contents of a variable at a {@link Log#DEBUG} log level. Supported variable types:
     * <ul>
     * <li>Primitive array: shows all values</li>
     * <li>{@link Object} array: shows all values</li>
     * <li>{@link JSONArray}: shows all values</li>
     * <li>{@link Iterable}: shows all values</li>
     * <li>{@link Map}: shows all keys with their values</li>
     * <li>{@link JSONObject}: shows all keys with their values</li>
     * <li>{@link Intent}: shows all extras in {@link Intent#getExtras()}</li>
     * <li>{@link Bundle}: shows all extras</li>
     * </ul>
     * All other objects will simply log {@link Object#toString()}.
     *
     * @param tag Used to identify the source of a log message.
     * @param object An object to log. May be null.
     * @param objectKey The key that points to this object for a {@link Map}, {@link Intent}, or
     * {@link Bundle}.
     * @param depth The depth of recursion. Used to indent nested objects.
     */
    private static void dump(String tag, Object object, Object objectKey, int depth) {
        if (depth == 0) {
            print(Log.DEBUG, tag, "Dumping object");
            printDivider();
        }

        StringBuilder sb = new StringBuilder();

        char[] spacesArray = new char[depth * 4];
        Arrays.fill(spacesArray, ' ');
        sb.append(new String(spacesArray));

        if (objectKey != null) {
            sb.append(objectKey + ": ");
        }

        if (object == null) {
            sb.append("null");
        } else {
            if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
                // Arrays aren't Iterable, so just print the values inline for primitive arrays.
                sb.append("[");
                for (int i = 0; i < Array.getLength(object); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(Array.get(object, i));
                }
                sb.append("]");
            } else {
                sb.append(object.toString());
            }
            sb.append(" (" + object.getClass().getName() + ")");
        }

        print(Log.DEBUG, tag, sb.toString());

        Iterable<?> iterable = null;
        if (object instanceof Object[]) {
            iterable = Arrays.asList((Object[])object);
        } else if (object instanceof JSONArray) {
            List<Integer> keys = new ArrayList<Integer>();
            for (int i = 0; i < ((JSONArray)object).length(); i++) {
                keys.add(i, i);
            }
            iterable = keys;
        } else if (object instanceof Iterable) {
            iterable = (Iterable<?>)object;
        } else if (object instanceof Map) {
            iterable = ((Map<?, ?>)object).keySet();
        } else if (object instanceof JSONObject) {
            List<String> keys = new ArrayList<String>();
            JSONArray names = ((JSONObject)object).names();
            for (int i = 0; i < names.length(); i++) {
                keys.add(i, names.optString(i));
            }
            iterable = keys;
        } else if (object instanceof Intent) {
            iterable = ((Intent)object).getExtras().keySet();
        } else if (object instanceof Bundle) {
            iterable = ((Bundle)object).keySet();
        }

        if (iterable != null) {
            for (Object key : iterable) {
                if (object.getClass().isArray() || object instanceof Iterable) {
                    dump(tag, key, null, depth + 1);
                } else if (object instanceof JSONArray) {
                    dump(tag, ((JSONArray)object).opt((Integer)key), null, depth + 1);
                } else {
                    Object value = null;
                    if (object instanceof Map) {
                        value = ((Map<?, ?>)object).get(key);
                    } else if (object instanceof JSONObject) {
                        value = ((JSONObject)object).opt((String)key);
                    } else if (object instanceof Intent) {
                        value = ((Intent)object).getExtras().get((String)key);
                    } else if (object instanceof Bundle) {
                        value = ((Bundle)object).get((String)key);
                    }

                    dump(tag, value, key, depth + 1);
                }
            }
        }

        if (depth == 0) {
            printDivider();
        }
    }
}
