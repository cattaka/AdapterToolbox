package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.SingleViewAdapter2;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleNumberAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class MultiAdapterExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    mSnackbarLogic.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    mSnackbarLogic.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    mSnackbarLogic.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    mSnackbarLogic.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    ListenerRelay<SingleViewAdapter2, SingleViewAdapter2.ViewHolder> mHeaderListenerRelay = new ListenerRelay<SingleViewAdapter2, SingleViewAdapter2.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull SingleViewAdapter2 adapter, @NonNull SingleViewAdapter2.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    mSnackbarLogic.make(view, "Strings Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    mSnackbarLogic.make(view, "Numbers Header is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull SingleViewAdapter2 adapter, @NonNull SingleViewAdapter2.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    mSnackbarLogic.make(view, "Strings Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    mSnackbarLogic.make(view, "Numbers Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.check_string_items) {
                if (buttonView.isChecked()) {
                    int position = 0;
                    mMergeRecyclerAdapter.addAdapter(position, mStringsAdapter, true);
                    mMergeRecyclerAdapter.addAdapter(position, mStringsHeaderAdapter, true);
                } else {
                    mMergeRecyclerAdapter.removeAdapter(mStringsAdapter, true);
                    mMergeRecyclerAdapter.removeAdapter(mStringsHeaderAdapter, true);
                }
            } else if (buttonView.getId() == R.id.check_number_items) {
                if (buttonView.isChecked()) {
                    int position = mMergeRecyclerAdapter.getSubAdapterCount();
                    mMergeRecyclerAdapter.addAdapter(position, mNumbersAdapter, true);
                    mMergeRecyclerAdapter.addAdapter(position, mNumbersHeaderAdapter, true);
                } else {
                    mMergeRecyclerAdapter.removeAdapter(mNumbersAdapter, true);
                    mMergeRecyclerAdapter.removeAdapter(mNumbersHeaderAdapter, true);
                }
            }
        }
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    CheckBox mStringItemsCheck;
    CheckBox mNumberItemsCheck;
    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter2 mStringsHeaderAdapter;
    SimpleStringAdapter mStringsAdapter;
    SingleViewAdapter2 mNumbersHeaderAdapter;
    SimpleNumberAdapter mNumbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mStringItemsCheck = (CheckBox) findViewById(R.id.check_string_items);
        mNumberItemsCheck = (CheckBox) findViewById(R.id.check_number_items);

        // bind event handlers
        mStringItemsCheck.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mNumberItemsCheck.setOnCheckedChangeListener(mOnCheckedChangeListener);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create strings header adapter
                mStringsHeaderAdapter = new SingleViewAdapter2(this, R.layout.view_header_string);
                mStringsHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mStringsHeaderAdapter);
            }
            {   // create strings adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add("item " + i);
                }

                mStringsAdapter = new SimpleStringAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mStringsAdapter);
            }
            {   // create numbers header adapter
                mNumbersHeaderAdapter = new SingleViewAdapter2(this, R.layout.view_header_number);
                mNumbersHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mNumbersHeaderAdapter);
            }
            {   // create numbers adapter
                List<Number> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add(i);
                }
                mNumbersAdapter = new SimpleNumberAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mNumbersAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }

}
