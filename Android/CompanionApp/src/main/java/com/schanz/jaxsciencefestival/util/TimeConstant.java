package com.schanz.jaxsciencefestival.util;

public final class TimeConstant {

    public static final long NANO_ONE_MILLIS = 1000000L;
    public static final long MILLIS_ONE_MINUTE = 1000L * 60; // 60,000
    public static final long MILLIS_ONE_HOUR = MILLIS_ONE_MINUTE * 60; // 3,600,000
    public static final long MILLIS_ONE_DAY = MILLIS_ONE_HOUR * 24; // 86,400,000
    public static final long MILLIS_SEVEN_DAYS = MILLIS_ONE_DAY * 7;
    public static final long MILLIS_THIRTY_DAYS = MILLIS_ONE_DAY * 30;
    public static final long MILLIS_SIXTY_DAYS = MILLIS_ONE_DAY * 60;

    private TimeConstant() {
    }

    public static double nanoToMillis(long nanoSeconds) {
        return nanoSeconds / (double) NANO_ONE_MILLIS;
    }

    public static double millisToMinutes(long millis) {
        return millis / (double) MILLIS_ONE_MINUTE;
    }

    public static long subtractDaysFromMillis(int daysToSubtract, long millisToSubtractFrom) {
        return millisToSubtractFrom - (daysToSubtract * MILLIS_ONE_DAY);
    }
}
