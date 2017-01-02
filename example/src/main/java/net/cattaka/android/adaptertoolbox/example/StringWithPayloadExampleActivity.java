package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.StringWithPayloadViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class StringWithPayloadExampleActivity extends AppCompatActivity {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (viewHolder instanceof StringWithPayloadViewHolderFactory.ViewHolder) {
                if (view.getId() == R.id.button_none) {
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition(), null);
                } else if (view.getId() == R.id.button_a) {
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition(), "A");
                } else if (view.getId() == R.id.button_b) {
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition(), "B");
                }
            }
        }
    };

    RecyclerView mRecyclerView;
    ScrambleAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_with_payload_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            mAdapter = new ScrambleAdapter<String>(this, items, mListenerRelay, new StringWithPayloadViewHolderFactory());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
