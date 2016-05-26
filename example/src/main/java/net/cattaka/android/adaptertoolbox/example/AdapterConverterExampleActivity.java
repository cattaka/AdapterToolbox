package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class AdapterConverterExampleActivity extends AppCompatActivity {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                String item = (String) adapter.getItemAt(viewHolder.getAdapterPosition());
                Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_converter_example);

        // find views
        mListView = (ListView) findViewById(R.id.list);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            SimpleStringAdapter adapter = new SimpleStringAdapter(this, items, mListenerRelay);
            AdapterConverter<ScrambleAdapter<String>, RecyclerView.ViewHolder, String> adapterConverter = new AdapterConverter<ScrambleAdapter<String>, RecyclerView.ViewHolder, String>(this, adapter);
            mListView.setAdapter(adapterConverter);
        }
    }
}
