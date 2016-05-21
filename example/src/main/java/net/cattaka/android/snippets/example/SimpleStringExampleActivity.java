package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SimpleStringExampleActivity extends AppCompatActivity {
    ListenerRelay<SimpleStringAdapter, SimpleStringAdapter.ViewHolder> mListenerRelay = new ListenerRelay<SimpleStringAdapter, SimpleStringAdapter.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, SimpleStringAdapter adapter, SimpleStringAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = adapter.getItemAt(viewHolder.getCompatPosition());
                Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, SimpleStringAdapter adapter, SimpleStringAdapter.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = adapter.getItemAt(viewHolder.getCompatPosition());
                Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_string_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            SimpleStringAdapter adapter = new SimpleStringAdapter(this, items);
            adapter.setListenerRelay(mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
