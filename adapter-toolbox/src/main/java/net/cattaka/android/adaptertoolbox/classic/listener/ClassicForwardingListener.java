package net.cattaka.android.adaptertoolbox.classic.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;

/**
 * Created by cattaka on 2016/05/12.
 */
public class ClassicForwardingListener<A extends RecyclerView.Adapter<? extends VH>, VH extends AdapterConverter.ViewHolder>
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
     * @see View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, view) : null);
            if (vh != null) {
                mListenerRelay.onClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, view);
            }
        }
    }

    /**
     * @see View.OnLongClickListener
     */
    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, view) : null);
            if (vh != null) {
                return mListenerRelay.onLongClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, view);
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
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, group) : null);
            if (vh != null) {
                mListenerRelay.onCheckedChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, group, checkedId);
            }
        }
    }

    /**
     * @see CompoundButton.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, buttonView) : null);
            if (vh != null) {
                mListenerRelay.onCheckedChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, buttonView, isChecked);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, seekBar) : null);
            if (vh != null) {
                mListenerRelay.onProgressChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar, progress, fromUser);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, seekBar) : null);
            if (vh != null) {
                mListenerRelay.onStartTrackingTouch(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar);
            }
        }
    }

    /**
     * @see SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, seekBar) : null);
            if (vh != null) {
                mListenerRelay.onStopTrackingTouch(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, parent) : null);
            if (vh != null) {
                mListenerRelay.onNothingSelected(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, parent);
            }
        }
    }

    /**
     * @see AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, parent) : null);
            if (vh != null) {
                mListenerRelay.onItemSelected(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, parent, view, position, id);
            }
        }
    }

    /**
     * @see TextView.OnEditorActionListener
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mListenerRelay != null) {
            RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
            @SuppressWarnings("unchecked")
            VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, v) : null);
            if (vh != null) {
                return mListenerRelay.onEditorAction(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, v, actionId, event);
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
                    RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
                    @SuppressWarnings("unchecked")
                    VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, target) : null);
                    if (vh != null) {
                        mListenerRelay.beforeTextChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, target, s, start, count, after);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListenerRelay != null) {
                    RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
                    @SuppressWarnings("unchecked")
                    VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, target) : null);
                    if (vh != null) {
                        mListenerRelay.onTextChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, target, s, start, count, count);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListenerRelay != null) {
                    RecyclerView recyclerView = mProvider.getAttachedRecyclerView();
                    @SuppressWarnings("unchecked")
                    VH vh = (VH) (recyclerView != null ? findContainingViewHolder(recyclerView, target) : null);
                    if (vh != null) {
                        mListenerRelay.afterTextChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, target, s);
                    }
                }
            }
        });
    }

    @Nullable
    public static AdapterConverter.ViewHolder findContainingViewHolder(@NonNull RecyclerView recyclerView, @NonNull View view) {
        View v = view;
        while (v != null && v.getParent() instanceof View) {
            if (v.getParent() == recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(v);
                if (viewHolder instanceof AdapterConverter.ViewHolder) {
                    return (AdapterConverter.ViewHolder) viewHolder;
                }
            }
            v = (View) v.getParent();
        }
        return null;
    }
}
