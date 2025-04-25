package com.faltenreich.diaguard.shared.data.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faltenreich.diaguard.feature.export.Backupable;
import com.j256.ormlite.field.DatabaseField;

import org.joda.time.format.DateTimeFormat;

public class FoodEaten extends BaseEntity implements Backupable {

    public static final String BACKUP_KEY = "foodEaten";

    public class Column extends BaseEntity.Column {
        public static final String AMOUNT_IN_GRAMS = "amountInGrams";
        public static final String MEAL = "meal";
        public static final String FOOD = "food";
    }

    @DatabaseField(columnName = Column.AMOUNT_IN_GRAMS)
    private float amountInGrams;

    @DatabaseField(columnName = Column.MEAL, foreign = true, foreignAutoRefresh = true)
    private Meal meal;

    @DatabaseField(columnName = Column.FOOD, foreign = true, foreignAutoRefresh = true)
    private Food food;

    public float getAmountInGrams() {
        return amountInGrams;
    }

    public void setAmountInGrams(float amountInGrams) {
        this.amountInGrams = amountInGrams;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public float getCarbohydrates() {
        return getFood() != null ? getAmountInGrams() * getFood().getCarbohydrates() / 100 : 0;
    }

    public boolean isValid() {
        return amountInGrams > 0;
    }

    @Nullable
    public String print() {
        int amountEaten = (int) getAmountInGrams();
        if (food != null && amountEaten > 0) {
            return String.format("%d g %s", amountEaten, food.getName());
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %f grams (updated: %s)",
            food.getName(),
            amountInGrams,
            DateTimeFormat.mediumDateTime().print(getUpdatedAt())
        );
    }

    @Override
    public String getKeyForBackup() {
        return BACKUP_KEY;
    }

    @Override
    public String[] getValuesForBackup() {
        return new String[]{food.getName(), Float.toString(amountInGrams)};
    }
}
