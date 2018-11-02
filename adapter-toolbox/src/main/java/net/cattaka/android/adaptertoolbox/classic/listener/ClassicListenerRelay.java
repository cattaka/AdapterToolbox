package net.cattaka.android.adaptertoolbox.classic.listener;

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

import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;

/**
 * Created by cattaka on 16/05/14.
 */
public class ClassicListenerRelay {

    /**
     * @see View.OnClickListener
     */
    public void onClick(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
    }

    /**
     * @see View.OnLongClickListener
     */
    public boolean onLongClick(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
        return false;
    }

    /**
     * @see RadioGroup.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, RadioGroup group, int checkedId) {
    }

    /**
     * @see CompoundButton.OnCheckedChangeListener
     */
    public void onCheckedChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull CompoundButton buttonView, boolean isChecked) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onProgressChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull SeekBar seekBar, int progress, boolean fromUser) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onStartTrackingTouch(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    public void onStopTrackingTouch(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull SeekBar seekBar) {
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    public void onNothingSelected(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull AdapterView<?> parent) {

    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    public void onItemSelected(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull AdapterView<?> parent, @NonNull View view, int itemPosition, long id) {
    }

    /**
     * @see TextView.OnEditorActionListener
     */
    public boolean onEditorAction(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull TextView v, int actionId, KeyEvent event) {
        return false;
    }

    /**
     * @see android.text.TextWatcher
     */
    public void beforeTextChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull TextView v, @NonNull CharSequence s, int start, int count, int after) {
    }

    /**
     * @see android.text.TextWatcher
     */
    public void onTextChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull TextView v, @NonNull CharSequence s, int start, int before, int count) {
    }

    /**
     * @see android.text.TextWatcher
     */
    public void afterTextChanged(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull TextView v, @NonNull Editable s) {
    }
}
