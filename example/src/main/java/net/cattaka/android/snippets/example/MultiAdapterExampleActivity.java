package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.SingleViewAdapter;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.SimpleNumberAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
import net.cattaka.android.snippets.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class MultiAdapterExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Snackbar.make(view, "Strings Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Snackbar.make(view, "Numbers Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Snackbar.make(view, "Footer is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, ScrambleAdapter<?> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Snackbar.make(view, "Strings Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Snackbar.make(view, "Numbers Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Snackbar.make(view, item + " is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Snackbar.make(view, "Footer is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter mStringsHeaderAdapter;
    SimpleStringAdapter mStringsAdapter;
    SingleViewAdapter mNumbersHeaderAdapter;
    SimpleNumberAdapter mNumbersAdapter;
    SingleViewAdapter mFooterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create strings header adapter
                mStringsHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header_string);
                mStringsHeaderAdapter.setListenerRelay(mListenerRelay);
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
                mNumbersHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header_number);
                mNumbersHeaderAdapter.setListenerRelay(mListenerRelay);
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
