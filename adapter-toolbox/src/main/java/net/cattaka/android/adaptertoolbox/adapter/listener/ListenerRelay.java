package net.cattaka.android.adaptertoolbox.adapter.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
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
        > {

    /**
     * @see android.view.View.OnClickListener
     */
    public void onClick(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull View view) {
    }

    /**
     * @see android.view.View.OnLongClickListener
     */
    public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull View view) {
        return false;
    }

    /**
     * @see RadioGroup.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, RadioGroup group, int checkedId) {
    }

    /**
     * @see android.widget.CompoundButton.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull CompoundButton buttonView, boolean isChecked) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onProgressChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull SeekBar seekBar, int progress, boolean fromUser) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onStartTrackingTouch(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    public void onStopTrackingTouch(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    public void onNothingSelected(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull AdapterView<?> parent) {

    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    public void onItemSelected(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
    }

    /**
     * @see android.widget.TextView.OnEditorActionListener
     */
    public boolean onEditorAction(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull TextView v, int actionId, KeyEvent event) {
        return false;
    }

    /**
     * @see android.text.TextWatcher
     */
    public void beforeTextChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull TextView v, @NonNull CharSequence s, int start, int count, int after) {
    }

    /**
     * @see android.text.TextWatcher
     */
    public void onTextChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull TextView v, @NonNull CharSequence s, int start, int before, int count) {
    }

    /**
     * @see android.text.TextWatcher
     */
    public void afterTextChanged(@NonNull RecyclerView recyclerView, @NonNull A adapter, @NonNull VH vh, @NonNull TextView v, @NonNull Editable s) {
    }
}
