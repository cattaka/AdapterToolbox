package net.cattaka.android.adaptertoolbox.example.utils;

import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by cattaka on 16/05/21.
 */
public class ExampleDataGenerator {
    public static List<MyTreeItem> generateMyTreeItem(List<Integer> numOfLevels, int level) {
        if (numOfLevels.size() == 0) {
            return null;
        }
        List<MyTreeItem> items = new ArrayList<>();
        int n = numOfLevels.get(0);
        for (int i = 0; i < n; i++) {
            String text = String.format(Locale.ROOT, "Item %d of level %d", i, level);
            List<MyTreeItem> children = generateMyTreeItem(numOfLevels.subList(1, numOfLevels.size()), level + 1);
            items.add(new MyTreeItem(text, children));
        }
        return items;
    }
}
