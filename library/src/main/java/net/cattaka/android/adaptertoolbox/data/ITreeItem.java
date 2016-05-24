package net.cattaka.android.adaptertoolbox.data;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public interface ITreeItem<T extends ITreeItem<T>> {
    List<T> getChildren();
}
