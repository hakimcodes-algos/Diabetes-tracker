package com.faltenreich.diaguard.feature.food.networking.dto;

import com.faltenreich.diaguard.shared.data.primitive.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faltenreich on 23.09.2016.
 */
public class ProductDto {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // Workaround: Sometimes a Number, sometimes a String
    @SerializedName("sortkey")
    public JsonElement identifier;

    @SerializedName("lang")
    public String languageCode;

    @SerializedName("product_name")
    public String name;

    @SerializedName("brands")
    public String brand;

    @SerializedName("ingredients_text")
    public String ingredients;

    @SerializedName("labels")
    public String labels;

    @SerializedName("nutriments")
    public NutrientsDto nutrients;

    @SerializedName("last_edit_dates_tags")
    public String[] lastEditDates;

    public boolean isValid() {
        return !StringUtils.isBlank(name) && nutrients != null && nutrients.carbohydrates != null;
    }
}
