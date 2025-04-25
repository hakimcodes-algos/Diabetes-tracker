package com.faltenreich.diaguard.feature.preference.data;

import androidx.annotation.StringRes;

import com.faltenreich.diaguard.R;

public enum  Daytime {

    MORNING(4, R.string.morning),
    NOON(10, R.string.noon),
    EVENING(16, R.string.evening),
    NIGHT(22, R.string.night);

    public static final int INTERVAL_LENGTH = 6;

    public final int startingHour;
    public @StringRes
    final int textResourceId;

    Daytime(int startingHour, @StringRes int textResourceId) {
        this.startingHour = startingHour;
        this.textResourceId = textResourceId;
    }

    public static Daytime toDayTime(int hourOfDay) {
        if (hourOfDay >=4 && hourOfDay < 10) {
            return MORNING;
        } else if (hourOfDay >= 10 && hourOfDay < 16) {
            return NOON;
        } else if (hourOfDay >= 16 && hourOfDay < 22) {
            return EVENING;
        } else {
            return NIGHT;
        }
    }

    public String toDeprecatedString() {
        switch (this) {
            case MORNING: return "Morning";
            case NOON: return "Noon";
            case EVENING: return "Evening";
            case NIGHT: return "Night";
            default: return null;
        }
    }
}
