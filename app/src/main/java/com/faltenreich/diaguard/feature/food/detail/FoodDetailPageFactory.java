package com.faltenreich.diaguard.feature.food.detail;

import androidx.fragment.app.Fragment;

import com.faltenreich.diaguard.feature.food.detail.history.FoodHistoryFragment;
import com.faltenreich.diaguard.feature.food.detail.info.FoodInfoFragment;
import com.faltenreich.diaguard.feature.food.detail.nutrient.NutrientListFragment;

class FoodDetailPageFactory {

    static Fragment createFragmentForPage(FoodDetailPage page, long foodId) {
        switch (page) {
            case INFO:
                return FoodInfoFragment.newInstance(foodId);
            case NUTRIENTS:
                return NutrientListFragment.newInstance(foodId);
            case HISTORY:
                return FoodHistoryFragment.newInstance(foodId);
            default:
                throw new IllegalArgumentException("Unknown fragment for page " + page);
        }
    }
}
