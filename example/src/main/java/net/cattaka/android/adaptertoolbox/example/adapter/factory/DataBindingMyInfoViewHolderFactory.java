package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import net.cattaka.android.adaptertoolbox.adapter.CodeLabelAdapter;
import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.ObservableMyInfo;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.databinding.ItemDataBindingMyInfoBinding;

/**
 * Created by cattaka on 16/05/15.
 */
public class DataBindingMyInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<DataBindingMyInfoViewHolderFactory.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_binding_my_info, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.mBinding.spinnerOrdinalLabel.setAdapter(CodeLabelAdapter.newInstance(view.getContext(), OrdinalLabel.values(), true));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewHolder holder, int position, Object object) {
        ObservableMyInfo item = (ObservableMyInfo) object;

        holder.mBinding.setMyInfo(item);
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof ObservableMyInfo;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            SeekBarBindingAdapter.OnProgressChanged,
            CompoundButton.OnCheckedChangeListener,
            AdapterView.OnItemSelectedListener {
        ItemDataBindingMyInfoBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.setHolder(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (compoundButton == mBinding.switchChecked) {
                mBinding.getMyInfo().setChecked(b);
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (adapterView == mBinding.spinnerOrdinalLabel) {
                mBinding.getMyInfo().setOrdinalLabel((OrdinalLabel) mBinding.spinnerOrdinalLabel.getSelectedItem());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            if (adapterView == mBinding.spinnerOrdinalLabel) {
                mBinding.getMyInfo().setOrdinalLabel(null);
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar == mBinding.seekIntValue) {
                mBinding.getMyInfo().setIntValue(progress);
            }
        }
    }
}
