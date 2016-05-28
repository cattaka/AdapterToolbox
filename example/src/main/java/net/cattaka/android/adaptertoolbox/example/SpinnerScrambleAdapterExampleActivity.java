package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleStringViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerScrambleAdapterExampleActivity extends AppCompatActivity {
    ClassicListenerRelay mListenerRelay = new ClassicListenerRelay() {
        @Override
        public void onClick(@NonNull AdapterView<?> adapterView, @NonNull ClassicScrambleAdapter<?> adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
            if (adapterView.getId() == R.id.list) {
                String item = (String) adapter.getItem(position);
                Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(@NonNull AdapterView<?> adapterView, @NonNull ClassicScrambleAdapter<?> adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
            if (adapterView.getId() == R.id.list) {
                String item = (String) adapter.getItem(position);
                Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_scramble_adapter_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        { // set adapter
            List<String> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                items.add("item " + i);
            }
            ClassicScrambleAdapter<String> adapter = new ClassicScrambleAdapter<String>(this, items, mListenerRelay, new SimpleStringViewHolderFactory());
            mSpinner.setAdapter(adapter);
        }
    }
}
