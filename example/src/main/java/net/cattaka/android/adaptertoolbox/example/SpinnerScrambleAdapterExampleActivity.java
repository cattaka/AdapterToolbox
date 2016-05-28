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
import net.cattaka.android.adaptertoolbox.example.adapter.spinner.HeaderInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.spinner.SpinnerStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.HeaderInfo;

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
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                items.add(new HeaderInfo("Header " + i));
                for (int j=0;j<4;j++) {
                    items.add("item " + i + "-" + j);
                }
            }
            ClassicScrambleAdapter<Object> adapter = new ClassicScrambleAdapter<Object>(
                    this,
                    items,
                    mListenerRelay,
                    new HeaderInfoViewHolderFactory(),
                    new SpinnerStringViewHolderFactory()
                    );
            // Issue: Spinner Doesn't Allow Heterogeneous ListAdapters in Lollipop. https://code.google.com/p/android/issues/detail?id=79011
            adapter.setRecyclingDisabled(true);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(1);
        }
    }
}
