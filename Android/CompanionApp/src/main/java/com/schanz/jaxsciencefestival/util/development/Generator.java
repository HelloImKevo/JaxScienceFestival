package com.schanz.jaxsciencefestival.util.development;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.schanz.jaxsciencefestival.R;

import java.util.Random;

public final class Generator {

    private Generator() {
    }

    @ColorRes
    public static int getRandomColorRes() {
        final int[] colors = new int[]{
                // Flat UI Colors
                R.color.blue_chambray,
                R.color.blue_dodger_blue,
                R.color.blue_royal_blue,
                R.color.green_shamrock,
                R.color.green_emerald,
                R.color.green_eucalyptus,
                R.color.orange_buttercup,
                R.color.orange_california,
                R.color.orange_ecstasy,
                R.color.purple_honey_flower,
                R.color.purple_plum,
                R.color.purple_wisteria,
                R.color.red_cinnabar,
                R.color.red_old_brick,
                R.color.red_pomegranate,
                R.color.red_thunderbird,
                R.color.yellow_ripe_lemon,
                R.color.yellow_saffron,

                // Futurist Palette
                R.color.black_disturbing,
                R.color.red_take_flight,
                R.color.gray_one,

                // SciFi Palette
                R.color.yellow_polished_gold,
                R.color.yellow_spoof,
                R.color.purple_universe,
                R.color.purple_magic,
                R.color.gray_down_pour,
                R.color.gray_stay_with_me,
                R.color.blue_voyager_sky,
                R.color.blue_vimeo,
                R.color.blue_atlantis,
                R.color.green_planetary_halo,
                R.color.green_summer_grass,
                R.color.green_aegean_sea,
                R.color.green_dewmist_delight,
                R.color.white_cocaine,
                R.color.black_space,
        };
        return colors[new Random().nextInt(colors.length)];
    }

    @ColorInt
    public static int getRandomColor(@NonNull Context context) {
        return ContextCompat.getColor(context, getRandomColorRes());
    }
}
