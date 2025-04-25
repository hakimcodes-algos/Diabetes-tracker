package com.faltenreich.diaguard.feature.preference.factor;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.databinding.ListItemFactorBinding;
import com.faltenreich.diaguard.feature.preference.data.Daytime;
import com.faltenreich.diaguard.feature.preference.data.TimeInterval;
import com.faltenreich.diaguard.shared.data.primitive.FloatUtils;
import com.faltenreich.diaguard.shared.view.recyclerview.viewholder.BaseViewHolder;

import org.joda.time.DateTimeConstants;

class FactorViewHolder extends BaseViewHolder<ListItemFactorBinding, FactorRangeItem> implements TextWatcher {

    private final Callback callback;

    FactorViewHolder(ViewGroup parent, Callback callback) {
        super(parent, R.layout.list_item_factor);
        this.callback = callback;
        initLayout();
    }

    @Override
    protected ListItemFactorBinding createBinding(View view) {
        return ListItemFactorBinding.bind(view);
    }

    private void initLayout() {
        getBinding().input.getEditText().setSelectAllOnFocus(true);
    }

    @Override
    protected void onBind(FactorRangeItem item) {
        setValue(item);
        setHint(item);
    }

    private void setValue(FactorRangeItem item) {
        EditText editText = getBinding().input.getEditText();
        editText.removeTextChangedListener(this);
        editText.setText(item.getValue() >= 0
            ? FloatUtils.parseFloat(item.getValue())
            : null);
        editText.addTextChangedListener(this);
    }

    private void setHint(FactorRangeItem item) {
        TimeInterval timeInterval = TimeInterval.ofRangeInHours(item.getRangeInHours());
        int hourOfDay = item.getHourOfDay();
        int target = (item.getHourOfDay() + item.getRangeInHours()) % DateTimeConstants.HOURS_PER_DAY;
        if (target == 0) {
            target = 24;
        }

        String hint;
        if (timeInterval == TimeInterval.EVERY_SIX_HOURS) {
            String timeOfDay = getContext().getString(Daytime.toDayTime(hourOfDay).textResourceId);
            hint = String.format("%s (%02d:00 - %02d:00)", timeOfDay, hourOfDay, target);
        } else {
            hint = String.format("%02d:00 - %02d:00", hourOfDay, target);
        }
        getBinding().input.setHint(hint);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        FactorRangeItem item = getItem();
        try {
            item.setValue(FloatUtils.parseNumber(editable.toString()));
            callback.onRangeItemChanged(item);
        } catch (NumberFormatException ignored) {

        }
    }

    interface Callback {
        void onRangeItemChanged(FactorRangeItem rangeItem);
    }
}
