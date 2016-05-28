package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;
import net.cattaka.android.adaptertoolbox.example.spinner.factory.HeaderInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.spinner.factory.SpinnerStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.HeaderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerScrambleAdapterExampleActivity extends AppCompatActivity {
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
                    null,
                    new HeaderInfoViewHolderFactory(),
                    new SpinnerStringViewHolderFactory()
                    );
            // Issue: Spinner Doesn't Allow Heterogeneous ListAdapters in Lollipop.
            // https://code.google.com/p/android/issues/detail?id=79011
            adapter.setRecyclingDisabled(true);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(1);
        }
    }
}
