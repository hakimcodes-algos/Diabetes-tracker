package com.faltenreich.diaguard.feature.preference.data;

import androidx.annotation.StringRes;

import com.faltenreich.diaguard.R;

public enum MealFactorUnit {

    CARBOHYDRATES_UNIT(0, R.string.unit_factor_carbohydrates_unit, .1f),
    BREAD_UNITS(1, R.string.unit_factor_bread_unit, .0833f);

    public final int index;
    @StringRes public final int titleResId;
    public final float factor;

    MealFactorUnit(int index, @StringRes int titleResId, float factor) {
        this.index = index;
        this.titleResId = titleResId;
        this.factor = factor;
    }
}
