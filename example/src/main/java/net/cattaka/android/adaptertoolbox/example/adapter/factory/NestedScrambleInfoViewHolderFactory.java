package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.NestedScrambleInfo;

/**
 * Created by cattaka on 16/05/16.
 */
public class NestedScrambleInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<NestedScrambleInfoViewHolderFactory.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_scramble, parent, false);

        {   // Hack height
            int h = parent.getHeight();
            if (h > 0) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = h / 3;
                view.setLayoutParams(params);
            }
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewHolder holder, int position, Object object) {
        NestedScrambleInfo item = (NestedScrambleInfo) object;

        ScrambleAdapter<Object> nestedAdapter = new ScrambleAdapter<>(holder.itemView.getContext(), item.getItems(), item.getListenerRelay(), item.getViewHolderFactories());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(nestedAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_nested);
        }
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof NestedScrambleInfo;
    }
}
