package net.cattaka.android.adaptertoolbox.classic.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
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
 * Created by cattaka on 2016/05/12.
 */
public class ClassicForwardingListener
        implements IForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder, ClassicListenerRelay<AdapterConverter.ViewHolder>>,
        View.OnClickListener,
        View.OnLongClickListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener,
        TextView.OnEditorActionListener {

    private IProvider<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder> mProvider;
    private ClassicListenerRelay<AdapterConverter.ViewHolder> mListenerRelay;

    public ClassicForwardingListener() {
    }

    @Override
    public void setListenerRelay(@Nullable ClassicListenerRelay<AdapterConverter.ViewHolder> listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    @Override
    public void setProvider(@NonNull IProvider<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder> provider) {
        mProvider = provider;
    }

    /**
     * @see View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(view);
            if (pair != null) {
                mListenerRelay.onClick(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, view);
            }
        }
    }

    /**
     * @see View.OnLongClickListener
     */
    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(view);
            if (pair != null) {
                return mListenerRelay.onLongClick(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, view);
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
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(group);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, group, checkedId);
            }
        }
    }

    /**
     * @see CompoundButton.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(buttonView);
            if (pair != null) {
                mListenerRelay.onCheckedChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, buttonView, isChecked);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onProgressChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, seekBar, progress, fromUser);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStartTrackingTouch(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, seekBar);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(seekBar);
            if (pair != null) {
                mListenerRelay.onStopTrackingTouch(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, seekBar);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onNothingSelected(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, parent);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(parent);
            if (pair != null) {
                mListenerRelay.onItemSelected(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, parent, view, position, id);
            }
        }
    }

    /**
     * @see TextView.OnEditorActionListener
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mListenerRelay != null) {
            Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(v);
            if (pair != null) {
                return mListenerRelay.onEditorAction(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, v, actionId, event);
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
                    Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.beforeTextChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, target, s, start, count, after);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListenerRelay != null) {
                    Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.onTextChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, target, s, start, count, count);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListenerRelay != null) {
                    Pair<AdapterView<?>, AdapterConverter.ViewHolder> pair = findContainingViewHolder(target);
                    if (pair != null) {
                        mListenerRelay.afterTextChanged(pair.first, mProvider.getAdapter().getParentAdapter(), pair.second, target, s);
                    }
                }
            }
        });
    }

    @Nullable
    public static Pair<AdapterView<?>, AdapterConverter.ViewHolder> findContainingViewHolder(@NonNull View view) {
        View v = view;
        while (v != null && v.getParent() instanceof View) {
            if (v.getParent() instanceof ListView) {
                Object tag = v.getTag(AdapterConverter.VIEW_HOLDER);
                if (tag instanceof AdapterConverter.ViewHolder) {
                    return new Pair<AdapterView<?>, AdapterConverter.ViewHolder>((AdapterView<?>) v, (AdapterConverter.ViewHolder) tag);
                }
            }
            v = (View) v.getParent();
        }
        return null;
    }
}
