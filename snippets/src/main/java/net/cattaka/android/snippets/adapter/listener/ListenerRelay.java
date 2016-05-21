package net.cattaka.android.snippets.adapter.listener;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by cattaka on 16/05/14.
 */
public class ListenerRelay<
        A extends RecyclerView.Adapter<? extends VH>,
        VH extends RecyclerView.ViewHolder
        > implements IListenerRelay<VH> {

    /**
     * @see android.view.View.OnClickListener
     */
    public void onClick(RecyclerView recyclerView, A adapter, VH vh, View view) {
    }

    /**
     * @see android.view.View.OnLongClickListener
     */
    public boolean onLongClick(RecyclerView recyclerView, A adapter, VH vh, View view) {
        return false;
    }

    /**
     * @see RadioGroup.OnCheckedChangeListener
     */
    public void onCheckedChanged(RecyclerView recyclerView, A adapter, VH vh, RadioGroup group, int checkedId) {
    }

    /**
     * @see android.widget.CompoundButton.OnCheckedChangeListener
     */
    public void onCheckedChanged(RecyclerView recyclerView, A adapter, VH vh, CompoundButton buttonView, boolean isChecked) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onProgressChanged(RecyclerView recyclerView, A adapter, VH vh, SeekBar seekBar, int progress, boolean fromUser) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onStartTrackingTouch(RecyclerView recyclerView, A adapter, VH vh, SeekBar seekBar) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onStopTrackingTouch(RecyclerView recyclerView, A adapter, VH vh, SeekBar seekBar) {
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    public void onNothingSelected(RecyclerView recyclerView, A adapter, VH vh, AdapterView<?> parent) {

    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    public void onItemSelected(RecyclerView recyclerView, A adapter, VH vh, AdapterView<?> parent, View view, int position, long id) {
    }

    /**
     * @see android.widget.TextView.OnEditorActionListener
     */
    public boolean onEditorAction(RecyclerView recyclerView, A adapter, VH vh, TextView v, int actionId, KeyEvent event) {
        return false;
    }

    /**
     * @see TextWatcher
     */
    public void beforeTextChanged(RecyclerView recyclerView, A adapter, VH vh, TextView v, CharSequence s, int start, int count, int after) {
    }

    /**
     * @see TextWatcher
     */
    public void onTextChanged(RecyclerView recyclerView, A adapter, VH vh, TextView v, CharSequence s, int start, int before, int count) {
    }

    /**
     * @see TextWatcher
     */
    public void afterTextChanged(RecyclerView recyclerView, A adapter, VH vh, TextView v, Editable s) {
    }
}
