package net.cattaka.android.adaptertoolbox.classic.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.adapter.listener.IListenerRelay;
import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.InnerScrambleAdapter;

/**
 * Created by cattaka on 16/05/14.
 */
public class ClassicListenerRelay<
        A extends InnerScrambleAdapter<?>,
        VH extends AdapterConverter.ViewHolder
        > implements IListenerRelay<VH> {

    /**
     * @see View.OnClickListener
     */
    public void onClick(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull View view) {
    }

    /**
     * @see View.OnLongClickListener
     */
    public boolean onLongClick(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull View view) {
        return false;
    }

    /**
     * @see RadioGroup.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, RadioGroup group, int checkedId) {
    }

    /**
     * @see CompoundButton.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull CompoundButton buttonView, boolean isChecked) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onProgressChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull SeekBar seekBar, int progress, boolean fromUser) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onStartTrackingTouch(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onStopTrackingTouch(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    public void onNothingSelected(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull AdapterView<?> parent) {

    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    public void onItemSelected(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
    }

    /**
     * @see TextView.OnEditorActionListener
     */
    public boolean onEditorAction(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull TextView v, int actionId, KeyEvent event) {
        return false;
    }

    /**
     * @see TextWatcher
     */
    public void beforeTextChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull TextView v, @NonNull CharSequence s, int start, int count, int after) {
    }

    /**
     * @see TextWatcher
     */
    public void onTextChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull TextView v, @NonNull CharSequence s, int start, int before, int count) {
    }

    /**
     * @see TextWatcher
     */
    public void afterTextChanged(@NonNull ListView listView, @NonNull ClassicScrambleAdapter<?> adapter, @NonNull VH vh, @NonNull TextView v, @NonNull Editable s) {
    }
}
