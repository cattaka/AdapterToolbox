package net.cattaka.android.adaptertoolbox.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.spinner.SpinnerMyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.utils.ExampleDataGenerator;
import net.cattaka.android.adaptertoolbox.utils.SpinnerUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerTreeItemAdapterExampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ClassicListenerRelay mListenerRelay = new ClassicListenerRelay() {
        @Override
        public void onClick(@NonNull AdapterView<?> adapterView, @NonNull AdapterConverter adapter, int position, @NonNull RecyclerView.ViewHolder vh, @NonNull View view) {
            super.onClick(adapterView, adapter, position, vh, view);
            if (adapter == mAdapterConverter) {
                SpinnerMyTreeItemAdapter.WrappedItem wrappedItem = (SpinnerMyTreeItemAdapter.WrappedItem) adapter.getItem(position);
                SpinnerUtils.selectSpinnerValue(mSpinner, wrappedItem);
                SpinnerUtils.dismissPopup(mSpinner);
            }
        }
    };

    Spinner mSpinner;
    AdapterConverterEx mAdapterConverter;

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
            mAdapterConverter = new AdapterConverterEx(this, adapter);
            adapter.setForwardingListener(new ClassicForwardingListener<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder>(mAdapterConverter, mListenerRelay));

            // Issue: Spinner Doesn't Allow Heterogeneous ListAdapters in Lollipop.
            // https://code.google.com/p/android/issues/detail?id=79011
            mAdapterConverter.setRecyclingDisabled(true);
            mSpinner.setAdapter(mAdapterConverter);
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

    private static class AdapterConverterEx extends AdapterConverter<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder, SpinnerMyTreeItemAdapter.WrappedItem> {
        public AdapterConverterEx(@NonNull Context context, @NonNull SpinnerMyTreeItemAdapter orig) {
            super(context, orig);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            @SuppressWarnings("unchecked")
            ViewHolderWrapper<SpinnerMyTreeItemAdapter.ViewHolder> vhw = (ViewHolderWrapper<SpinnerMyTreeItemAdapter.ViewHolder>) view.getTag(VIEW_HOLDER);
            vhw.getOrig().levelSpace.setVisibility(View.GONE);
            vhw.getOrig().openedCheck.setVisibility(View.GONE);
            return view;
        }
    }
}
