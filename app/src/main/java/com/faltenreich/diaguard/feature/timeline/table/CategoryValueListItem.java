package com.faltenreich.diaguard.feature.timeline.table;

import com.faltenreich.diaguard.shared.data.database.entity.Category;
import com.faltenreich.diaguard.feature.preference.data.PreferenceStore;
import com.faltenreich.diaguard.shared.data.primitive.FloatUtils;

/**
 * Created by Faltenreich on 15.12.2015.
 */
public class CategoryValueListItem extends CategoryListItem {

    private float valueOne;
    private float valueTwo;
    private float valueThree;

    public CategoryValueListItem(Category category) {
        super(category);
    }

    public CategoryValueListItem(Category category, float valueOne, float valueTwo, float valueThree) {
        super(category);
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.valueThree = valueThree;
    }

    public float getValueOne() {
        return valueOne;
    }

    public void setValueOne(float valueOne) {
        this.valueOne = valueOne;
    }

    public float getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(float valueTwo) {
        this.valueTwo = valueTwo;
    }

    public float getValueThree() {
        return valueThree;
    }

    public void setValueThree(float valueThree) {
        this.valueThree = valueThree;
    }

    public float getValueTotal() {
        return valueOne + valueTwo + valueThree;
    }

    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getCategory().stackValues()) {
            float value = getValueTotal();
            if (value > 0) {
                value = PreferenceStore.getInstance().formatDefaultToCustomUnit(getCategory(), getValueTotal());
                stringBuilder.append(FloatUtils.parseFloat(value));
            }
        } else {
            if (getValueOne() > 0) {
                float value = PreferenceStore.getInstance().formatDefaultToCustomUnit(getCategory(), getValueOne());
                stringBuilder.append(FloatUtils.parseFloat(value));
            }
            if (getValueTwo() > 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("\n");
                }
                float value = PreferenceStore.getInstance().formatDefaultToCustomUnit(getCategory(), getValueTwo());
                stringBuilder.append(FloatUtils.parseFloat(value));
            }
            if (getValueThree() > 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("\n");
                }
                float value = PreferenceStore.getInstance().formatDefaultToCustomUnit(getCategory(), getValueThree());
                stringBuilder.append(FloatUtils.parseFloat(value));
            }
        }
        return stringBuilder.toString();
    }
}
