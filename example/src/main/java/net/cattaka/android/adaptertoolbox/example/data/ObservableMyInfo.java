package net.cattaka.android.adaptertoolbox.example.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import net.cattaka.android.adaptertoolbox.example.BR;

/**
 * Created by cattaka on 16/05/15.
 */
public class ObservableMyInfo extends BaseObservable {
    @Bindable
    private int intValue;
    @Bindable
    private OrdinalLabel ordinalLabel;
    @Bindable
    private boolean checked;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
        notifyPropertyChanged(BR.intValue);
    }

    public OrdinalLabel getOrdinalLabel() {
        return ordinalLabel;
    }

    public void setOrdinalLabel(OrdinalLabel ordinalLabel) {
        this.ordinalLabel = ordinalLabel;
        notifyPropertyChanged(BR.ordinalLabel);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }
}
