package net.cattaka.android.snippets.adapter.listener;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import net.cattaka.android.snippets.R;

/**
 * Created by takao on 2016/05/12.
 */
public class ForwardingListener<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder>
        implements IForwardingListener<A, VH, ListenerRelay<A, ? super VH>>,
        View.OnClickListener,
        View.OnLongClickListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener,
        TextView.OnEditorActionListener {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private IProvider<A, VH> mProvider;
    private ListenerRelay<A, ? super VH> mListenerRelay;

    public ForwardingListener() {
    }

    @Override
    public void setListenerRelay(ListenerRelay<A, ? super VH> listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    @Override
    public void setProvider(IProvider<A, VH> provider) {
        mProvider = provider;
    }

    /**
     * @see android.view.View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, view);
            }
        }
    }

    /**
     * @see android.view.View.OnLongClickListener
     */
    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            if (vh != null) {
                return mListenerRelay.onLongClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, view);
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
            @SuppressWarnings("unchecked")
            VH vh = (VH) group.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onCheckedChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, group, checkedId);
            }
        }
    }

    /**
     * @see android.widget.CompoundButton.OnCheckedChangeListener
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) buttonView.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onCheckedChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, buttonView, isChecked);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) seekBar.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onProgressChanged(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar, progress, fromUser);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) seekBar.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onStartTrackingTouch(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar);
            }
        }
    }

    /**
     * @see android.widget.SeekBar.OnSeekBarChangeListener
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) seekBar.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onStopTrackingTouch(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, seekBar);
            }
        }
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) parent.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onNothingSelected(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, parent);
            }
        }
    }

    /**
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) parent.getTag(VIEW_HOLDER);
            if (vh != null) {
                mListenerRelay.onItemSelected(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, parent, view, position, id);
            }
        }
    }

    /**
     * @see android.widget.TextView.OnEditorActionListener
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) v.getTag(VIEW_HOLDER);
            if (vh != null) {
                return mListenerRelay.onEditorAction(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), vh, v, actionId,event);
            }
        }
        return false;
    }
}
