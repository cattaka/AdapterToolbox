package net.cattaka.android.adaptertoolbox.example.data;

import android.app.Activity;
import android.content.res.Resources;
import androidx.annotation.StringRes;

import net.cattaka.android.adaptertoolbox.data.ITreeItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/22.
 */
public class ActivityEntry implements ITreeItem<ActivityEntry> {
    @StringRes
    private int labelResId;
    private Class<? extends Activity> clazz;
    private List<ActivityEntry> children;

    public ActivityEntry(@StringRes int labelResId, Class<? extends Activity> clazz, ActivityEntry... children) {
        this.labelResId = labelResId;
        this.clazz = clazz;
        this.children = Arrays.asList(children);
    }

    public String getLabel(Resources res) {
        return res.getString(labelResId);
    }

    public Class<? extends Activity> getClazz() {
        return clazz;
    }

    @Override
    public List<ActivityEntry> getChildren() {
        return children;
    }
}
