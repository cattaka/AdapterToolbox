package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/01.
 */
public class CodeLabelAdapter extends ArrayAdapter<ICodeLabel> {
    @LayoutRes
    private int mResource;
    @LayoutRes
    private int mDropDownResource;

    public static CodeLabelAdapter newInstance(Context context, ICodeLabel[] objects, boolean withNullValue) {
        if (withNullValue) {
            List<ICodeLabel> values = new ArrayList<>();
            values.add(null);
            values.addAll(Arrays.asList(objects));
            return new CodeLabelAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, values);
        } else {
            return new CodeLabelAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, objects);
        }
    }

    public static CodeLabelAdapter newInstance(Context context, List<ICodeLabel> objects, boolean withNullValue) {
        if (withNullValue) {
            List<ICodeLabel> values = new ArrayList<>();
            values.add(null);
            values.addAll(objects);
            return new CodeLabelAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, values);
        } else {
            return new CodeLabelAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, objects);
        }
    }

    public CodeLabelAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, ICodeLabel[] objects) {
        super(context, resource, objects);
        mResource = resource;
        mDropDownResource = dropDownResource;
    }

    public CodeLabelAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, List<ICodeLabel> objects) {
        super(context, resource, objects);
        mResource = resource;
        mDropDownResource = dropDownResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(getContext()).inflate(mResource, null);
        } else {
            view = (TextView) convertView;
        }
        ICodeLabel item = getItem(position);
        if (item != null) {
            view.setText(item.getLabel(getContext().getResources()));
        } else {
            view.setText("");
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(getContext()).inflate(mDropDownResource, parent, false);
        } else {
            view = (TextView) convertView;
        }
        ICodeLabel item = getItem(position);
        if (item != null) {
            view.setText(item.getLabel(getContext().getResources()));
        } else {
            view.setText("");
        }
        return view;
    }
}
