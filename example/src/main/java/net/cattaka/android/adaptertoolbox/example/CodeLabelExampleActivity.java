package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.adapter.CodeLabelAdapter;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

/**
 * Created by cattaka on 16/05/02.
 */
public class CodeLabelExampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SnackbarLogic mSnackbarLogic = new SnackbarLogic();
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_label_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // set adapter
        mSpinner.setAdapter(CodeLabelAdapter.newInstance(this, OrdinalLabel.values(), true));
        mSpinner.setSelection(0, false);

        // bind event handlers
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            OrdinalLabel item = (OrdinalLabel) parent.getItemAtPosition(position);
            String text = (item != null) ? item.getLabel(getResources()) : "null";
            mSnackbarLogic.make(view, text + " is selected.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }
}
