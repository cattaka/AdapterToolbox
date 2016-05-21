package net.cattaka.android.snippets.example.data;

import net.cattaka.android.snippets.data.ITreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class MyTreeItem implements ITreeItem<MyTreeItem> {
    private String text;
    private List<MyTreeItem> children;

    public MyTreeItem() {
    }

    public MyTreeItem(String text, List<MyTreeItem> children) {
        this.text = text;
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public List<MyTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<MyTreeItem> children) {
        this.children = children;
    }
}
