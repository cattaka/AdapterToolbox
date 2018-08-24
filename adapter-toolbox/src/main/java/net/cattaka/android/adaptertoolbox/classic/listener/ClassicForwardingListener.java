package net.cattaka.android.adaptertoolbox.classic.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;

/**
 * Created by cattaka on 2016/05/12.
 */
public class ClassicForwardingListener<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder>
        extends ForwardingListener<A, VH> {
    AdapterConverter mAdapter;
    ClassicListenerRelay mListenerRelay;

    public ClassicForwardingListener() {
    }

    public ClassicForwardingListener(AdapterConverter adapter, ClassicListenerRelay listenerRelay) {
        mAdapter = adapter;
        mListenerRelay = listenerRelay;
    }

    public AdapterConverter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(AdapterConverter adapter) {
        mAdapter = adapter;
    }

    public ClassicListenerRelay getClassicListenerRelay() {
        return mListenerRelay;
    }

    public void setClassicListenerRelay(ClassicListenerRelay listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    /**
     * @see View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(view);
            if (pair != null) {
                mListenerRelay.onClick(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), view);
            }
        }
    }

    /**
     * @see View.OnLongClickListener
     */
    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(view);
            if (pair != null) {
                return mListenerRelay.onLongClick(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), view);
            }
        }
        return false;
    }

    /**
     * @see RadioGroup.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(group);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), group, checkedId);
            }
        }
    }

    /**
     * @see CompoundButton.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(buttonView);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), buttonView, isChecked);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onProgressChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), seekBar, progress, fromUser);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStartTrackingTouch(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), seekBar);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStopTrackingTouch(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), seekBar);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onNothingSelected(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), parent);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onItemSelected(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), parent, view, position, id);
            }
        }
    }

    /**
     * @see TextView.OnEditorActionListener
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(v);
            if (pair != null) {
                return mListenerRelay.onEditorAction(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), v, actionId, event);
            }
        }
        return false;
    }

    /**
     * @see TextWatcher
     */
    public void addTextChangedListener(@NonNull final EditText target) {
        target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (mListenerRelay != null) {
                    Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.beforeTextChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), target, s, start, count, after);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListenerRelay != null) {
                    Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.onTextChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), target, s, start, count, count);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListenerRelay != null) {
                    Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.afterTextChanged(pair.first, mAdapter, pair.second.getPosition(), pair.second.getOrig(), target, s);
                    }
                }
            }
        });
    }

    @Nullable
    public Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper> findContainingViewHolder(@NonNull View view) {
        View v = view;
        while (v != null && v.getParent() instanceof View) {
            if (v.getParent() instanceof AdapterView) {
                Object tag = v.getTag(AdapterConverter.VIEW_HOLDER);
                if (tag instanceof AdapterConverter.ViewHolderWrapper) {
                    return new Pair<AdapterView<?>, AdapterConverter.ViewHolderWrapper>((AdapterView<?>) v.getParent(), (AdapterConverter.ViewHolderWrapper) tag);
                }
            }
            v = (View) v.getParent();
        }
        return null;
    }
}
