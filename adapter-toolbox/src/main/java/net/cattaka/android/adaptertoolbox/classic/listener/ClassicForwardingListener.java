package net.cattaka.android.adaptertoolbox.classic.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.InnerScrambleAdapter;

/**
 * Created by cattaka on 16/05/27.
 */
public class ClassicForwardingListener<A extends InnerScrambleAdapter<?>, VH extends AdapterConverter.ViewHolder>
        implements IForwardingListener<A, VH, ClassicListenerRelay<A, VH>>,
        View.OnClickListener,
        View.OnLongClickListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener,
        TextView.OnEditorActionListener {

    private IProvider<A, VH> mProvider;
    private ClassicListenerRelay<A, VH> mListenerRelay;

    public ClassicForwardingListener() {
    }

    @Override
    public void setListenerRelay(@Nullable ClassicListenerRelay<A, VH> listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    @Override
    public void setProvider(@NonNull IProvider<A, VH> provider) {
        mProvider = provider;
    }

    /**
     * @see android.view.View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(view);
            if (pair != null) {
                mListenerRelay.onClick(pair.first, mProvider.getAdapter(), pair.second, view);
            }
        }
    }

    /**
     * @see android.view.View.OnLongClickListener
     */
    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(view);
            if (pair != null) {
                return mListenerRelay.onLongClick(pair.first, mProvider.getAdapter(), pair.second, view);
            }
        }
        return false;
    }

    /**
     * @see android.widget.RadioGroup.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(group);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mProvider.getAdapter(), pair.second, group, checkedId);
            }
        }
    }

    /**
     * @see android.widget.CompoundButton.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(buttonView);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mProvider.getAdapter(), pair.second, buttonView, isChecked);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onProgressChanged(pair.first, mProvider.getAdapter(), pair.second, seekBar, progress, fromUser);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStartTrackingTouch(pair.first, mProvider.getAdapter(), pair.second, seekBar);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStopTrackingTouch(pair.first, mProvider.getAdapter(), pair.second, seekBar);
            }
        }
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onNothingSelected(pair.first, mProvider.getAdapter(), pair.second, parent);
            }
        }
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onItemSelected(pair.first, mProvider.getAdapter(), pair.second, parent, view, position, id);
            }
        }
    }

    /**
     * @see android.widget.TextView.OnEditorActionListener
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mListenerRelay != null) {
            Pair<ListView, VH> pair = findContainingViewHolder(v);
            if (pair != null) {
                return mListenerRelay.onEditorAction(pair.first, mProvider.getAdapter(), pair.second, v, actionId, event);
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
                    Pair<ListView, VH> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.beforeTextChanged(pair.first, mProvider.getAdapter(), pair.second, target, s, start, count, after);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListenerRelay != null) {
                    Pair<ListView, VH> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.onTextChanged(pair.first, mProvider.getAdapter(), pair.second, target, s, start, count, count);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListenerRelay != null) {
                    Pair<ListView, VH> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.afterTextChanged(pair.first, mProvider.getAdapter(), pair.second, target, s);
                    }
                }
            }
        });
    }

    @Nullable
    public static <VH extends AdapterConverter.ViewHolder> Pair<ListView, VH> findContainingViewHolder(@NonNull View view) {
        View v = view;
        VH vh = null;
        while (v != null && v.getParent() instanceof View) {
            Object tag = v.getTag(AdapterConverter.VIEW_HOLDER);
            if (tag instanceof AdapterConverter.ViewHolder) {
                @SuppressWarnings("unchecked")
                VH t = (VH) tag;
                vh = t;
            }
            if (v.getParent() instanceof ListView && vh != null) {
                return new Pair<>((ListView) v.getParent(), vh);
            }
            v = (View) v.getParent();
        }
        return null;
    }
}
