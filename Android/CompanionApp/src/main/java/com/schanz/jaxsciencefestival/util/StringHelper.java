package com.schanz.jaxsciencefestival.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringHelper {

    // The Yahoo Finance API will sometimes abbreviate thousands with a "K"

    public static final String ABBR_MILLION = "M";
    public static final String ABBR_BILLION = "B";
    public static final String ABBR_TRILLION = "T";

    private StringHelper() {
    }

    @Nullable
    public static String toDateAtTime(@Nullable Date date) {
        return date != null
                ? new SimpleDateFormat("EEEE MMM dd, yyyy '@' h:mm a", Locale.getDefault()).format(date)
                : null;
    }

    @NonNull
    public static String toPrice(@Nullable BigDecimal bigDecimal) {
        return bigDecimal != null
                ? NumberFormat.getCurrencyInstance(Locale.getDefault()).format(bigDecimal)
                : NumberFormat.getCurrencyInstance(Locale.getDefault()).format(BigDecimal.ZERO);
    }

    /**
     * @return String formatted to 2 decimal places, and prefixed with a "+" if positive.
     */
    public static String toChange(@Nullable BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            bigDecimal = BigDecimal.ZERO;
        }
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        if (bigDecimal.signum() > 0) {
            return "+" + bigDecimal;
        } else {
            return bigDecimal.toString();
        }
    }

    @Nullable
    public static String stripNonNumeric(@Nullable String string) {
        return string != null ? string.replaceAll("[^\\d.-]", "") : null;
    }

    @Nullable
    public static BigDecimal toBigDecimal(String input) {
        String prepared = StringHelper.stripNonNumeric(input);
        if (TextUtils.isEmpty(prepared)) {
            return null;
        }
        try {
            return new BigDecimal(prepared);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @NonNull
    public static BigDecimal fromAbbreviatedString(String input) {
        String prepared = stripNonNumeric(input);
        BigDecimal value = BigDecimal.ZERO;
        if (prepared != null && !prepared.isEmpty()) {
            prepared = prepared.toUpperCase(Locale.getDefault());
            try {
                value = new BigDecimal(prepared);
            } catch (NumberFormatException e) {
                return value;
            }
            if (input.contains(ABBR_MILLION)) {
                return value.multiply(BigDecimalConstant.ONE_MILLION);
            } else if (input.contains(ABBR_BILLION)) {
                return value.multiply(BigDecimalConstant.ONE_BILLION);
            } else if (input.contains(ABBR_TRILLION)) {
                return value.multiply(BigDecimalConstant.ONE_TRILLION);
            } else {
                return value;
            }
        }
        return value;
    }

    @NonNull
    public static String toAbbreviatedString(BigDecimal input) {
        if (input != null && input.signum() != 0) {
            final DecimalFormat decimalFormat = new DecimalFormat("0.0##");
            final int roundingMode = BigDecimal.ROUND_HALF_UP;
            if (input.compareTo(BigDecimalConstant.ONE_TRILLION) >= 0) {
                BigDecimal result = input.divide(BigDecimalConstant.ONE_TRILLION, 3, roundingMode);
                return decimalFormat.format(result) + ABBR_TRILLION;
            } else if (input.compareTo(BigDecimalConstant.ONE_BILLION) >= 0) {
                BigDecimal result = input.divide(BigDecimalConstant.ONE_BILLION, 3, roundingMode);
                return decimalFormat.format(result) + ABBR_BILLION;
            } else if (input.compareTo(BigDecimalConstant.ONE_MILLION) >= 0) {
                BigDecimal result = input.divide(BigDecimalConstant.ONE_MILLION, 3, roundingMode);
                return decimalFormat.format(result) + ABBR_MILLION;
            }
        }
        return input != null ? input.toString() : BigDecimal.ZERO.toString();
    }
}
