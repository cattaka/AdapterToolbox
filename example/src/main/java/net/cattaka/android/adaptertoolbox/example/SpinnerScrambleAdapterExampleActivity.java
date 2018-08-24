package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.data.HeaderInfo;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.spinner.factory.SpinnerHeaderInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.spinner.factory.SpinnerStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.utils.SpinnerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerScrambleAdapterExampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_scramble_adapter_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // Bind event handler
        mSpinner.setOnItemSelectedListener(this);

        { // set adapter
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                items.add(new HeaderInfo("Header " + i));
                for (int j = 0; j < 4; j++) {
                    items.add("item " + i + "-" + j);
                }
            }
            ClassicScrambleAdapter<Object> adapter = new ClassicScrambleAdapter<Object>(
                    this,
                    items,
                    null,
                    new SpinnerHeaderInfoViewHolderFactory(),
                    new SpinnerStringViewHolderFactory()
            );
            // Issue: Spinner Doesn't Allow Heterogeneous ListAdapters in Lollipop.
            // https://code.google.com/p/android/issues/detail?id=79011
            adapter.setRecyclingDisabled(true);
            mSpinner.setAdapter(adapter);
            SpinnerUtils.selectSpinnerValue(mSpinner, adapter.getItem(1), true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            Object item = parent.getSelectedItem();
            mSnackbarLogic.make(parent, item + " is selected.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }
}
