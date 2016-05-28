package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.spinner.SpinnerMyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.utils.ExampleDataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerTreeItemAdapterExampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_tree_item_adapter_example);

        // find views
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // bind event handlers
        mSpinner.setOnItemSelectedListener(this);

        { // set adapter
            List<MyTreeItem> items = ExampleDataGenerator.generateMyTreeItem(Arrays.asList(5, 3, 2), 0);
            SpinnerMyTreeItemAdapter adapter = new SpinnerMyTreeItemAdapter(this, items);
            AdapterConverter<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder, SpinnerMyTreeItemAdapter.WrappedItem> adapterConverter = new AdapterConverter<>(this, adapter);

            // Issue: Spinner Doesn't Allow Heterogeneous ListAdapters in Lollipop.
            // https://code.google.com/p/android/issues/detail?id=79011
            adapterConverter.setRecyclingDisabled(true);
            mSpinner.setAdapter(adapterConverter);
            mSpinner.setSelection(1);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            SpinnerMyTreeItemAdapter.WrappedItem wrappedItem = (SpinnerMyTreeItemAdapter.WrappedItem) parent.getItemAtPosition(position);
            MyTreeItem item = wrappedItem.getItem();
            String text = (item != null) ? item.getText() : "null";
            Toast.makeText(this, text + " is selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }
}
