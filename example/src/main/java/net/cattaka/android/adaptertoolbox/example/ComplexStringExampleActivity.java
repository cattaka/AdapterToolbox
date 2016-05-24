package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.ComplexStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        Snackbar.make(view, item + " is clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        Snackbar.make(view, item + " is clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        Snackbar.make(view, item + " is clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                switch (view.getId()) {
                    case R.id.text: {
                        Snackbar.make(view, item + " is long clicked.(Text)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_a: {
                        Snackbar.make(view, item + " is long clicked.(A)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.button_b: {
                        Snackbar.make(view, item + " is long clicked.(B)", Snackbar.LENGTH_SHORT).show();
                        break;
                    }
                }
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
            ComplexStringAdapter adapter = new ComplexStringAdapter(this, items, mListenerRelay);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapter);
        }
    }
}
