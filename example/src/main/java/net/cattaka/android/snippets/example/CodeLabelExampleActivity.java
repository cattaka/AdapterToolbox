package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import net.cattaka.android.snippets.adapter.CodeLabelAdapter;
import net.cattaka.android.snippets.example.data.OrdinalLabel;

/**
 * Created by cattaka on 16/05/02.
 */
public class CodeLabelExampleActivity extends AppCompatActivity {
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_label_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // set adapter
        mSpinner.setAdapter(CodeLabelAdapter.newInstance(this, OrdinalLabel.values(), true));
    }
}
