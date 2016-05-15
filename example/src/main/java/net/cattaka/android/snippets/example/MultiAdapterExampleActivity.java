package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.adapter.SingleViewAdapter;
import net.cattaka.android.snippets.adapter.listener.IListenerRelay;
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

    ListenerRelay<CustomRecyclerAdapter<RecyclerView.ViewHolder, Object>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<CustomRecyclerAdapter<RecyclerView.ViewHolder, Object>, RecyclerView.ViewHolder>() {
        @Override
        public void onItemClick(RecyclerView parent, CustomRecyclerAdapter<RecyclerView.ViewHolder, Object> adapter, int position,
                                int id, RecyclerView.ViewHolder vh) {
            if (parent.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(position);
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Toast.makeText(me, "Strings Header is clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Toast.makeText(me, item + " is clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Toast.makeText(me, "Numbers Header is clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Toast.makeText(me, item + " is clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Toast.makeText(me, "Footer is clicked.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onItemLongClick(RecyclerView parent, CustomRecyclerAdapter<RecyclerView.ViewHolder, Object> adapter,
                                       int position, int id, View view, RecyclerView.ViewHolder vh) {
            if (parent.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(position);
                if (la.mAdapter == mStringsHeaderAdapter) {
                    Toast.makeText(me, "Strings Header is long clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mStringsAdapter) {
                    String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                    Toast.makeText(me, item + " is long clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersHeaderAdapter) {
                    Toast.makeText(me, "Numbers Header is long clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mNumbersAdapter) {
                    Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                    Toast.makeText(me, item + " is long clicked.", Toast.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Toast.makeText(me, "Footer is long clicked.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    MultiAdapterExampleActivity me = this;
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

                mStringsAdapter = new SimpleStringAdapter(this, items);
                mStringsAdapter.setListenerRelay(mListenerRelay);
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
                mNumbersAdapter = new SimpleNumberAdapter(this, items);
                mNumbersAdapter.setListenerRelay(mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mNumbersAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }

}
