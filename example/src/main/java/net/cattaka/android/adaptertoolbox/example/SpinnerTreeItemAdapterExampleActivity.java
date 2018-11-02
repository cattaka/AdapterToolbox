package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
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

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

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

            mAdapterConverter = new AdapterConverterEx();
            SpinnerMyTreeItemAdapter adapter = new SpinnerMyTreeItemAdapter(this, items) {
                @NonNull
                @Override
                public ForwardingListener<SpinnerMyTreeItemAdapter, ViewHolder> createForwardingListener() {
                    return new ClassicForwardingListener<>(mAdapterConverter, mListenerRelay);
                }
            };
            mAdapterConverter.setOriginal(adapter);

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
            mSnackbarLogic.make(parent, text + " is selected.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }

    private static class AdapterConverterEx extends AdapterConverter<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder, SpinnerMyTreeItemAdapter.WrappedItem> {
        public AdapterConverterEx() {
            super();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            @SuppressWarnings("unchecked")
            ViewHolderWrapper<SpinnerMyTreeItemAdapter.ViewHolder> vhw = (ViewHolderWrapper<SpinnerMyTreeItemAdapter.ViewHolder>) view.getTag(VIEW_HOLDER);
            vhw.getOrig().levelSpace.setVisibility(View.GONE);
            vhw.getOrig().openedCheck.setVisibility(View.GONE);
            view.setClickable(false);
            return view;
        }
    }
}
