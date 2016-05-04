package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.thirdparty.MergeRecyclerAdapter;
import net.cattaka.android.snippets.adapter.SingleViewAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleNumberAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class MultiAdapterExampleActivity extends AppCompatActivity implements CustomRecyclerAdapter.OnItemClickListener, CustomRecyclerAdapter.OnItemLongClickListener {
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
                mStringsHeaderAdapter.setOnItemClickListener(this);
                mStringsHeaderAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mStringsHeaderAdapter);
            }
            {   // create strings adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add("item " + i);
                }
                mStringsAdapter = new SimpleStringAdapter(this, items);
                mStringsAdapter.setOnItemClickListener(this);
                mStringsAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mStringsAdapter);
            }
            {   // create numbers header adapter
                mNumbersHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header_number);
                mNumbersHeaderAdapter.setOnItemClickListener(this);
                mNumbersHeaderAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mNumbersHeaderAdapter);
            }
            {   // create numbers adapter
                List<Number> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add(i);
                }
                mNumbersAdapter = new SimpleNumberAdapter(this, items);
                mNumbersAdapter.setOnItemClickListener(this);
                mNumbersAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mNumbersAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, CustomRecyclerAdapter adapter, int position,
                            int id, RecyclerView.ViewHolder vh) {
        if (parent.getId() == R.id.recycler) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(position);
            if (la.mAdapter == mStringsHeaderAdapter) {
                Toast.makeText(this, "Strings Header is clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mStringsAdapter) {
                String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                Toast.makeText(this, item + " is clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mNumbersHeaderAdapter) {
                Toast.makeText(this, "Numbers Header is clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mNumbersAdapter) {
                Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                Toast.makeText(this, item + " is clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mFooterAdapter) {
                Toast.makeText(this, "Footer is clicked.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView parent, CustomRecyclerAdapter adapter,
                                   int position, int id, View view, RecyclerView.ViewHolder vh) {
        if (parent.getId() == R.id.recycler) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(position);
            if (la.mAdapter == mStringsHeaderAdapter) {
                Toast.makeText(this, "Strings Header is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mStringsAdapter) {
                String item = mStringsAdapter.getItemAt(la.mLocalPosition);
                Toast.makeText(this, item + " is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mNumbersHeaderAdapter) {
                Toast.makeText(this, "Numbers Header is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mNumbersAdapter) {
                Number item = mNumbersAdapter.getItemAt(la.mLocalPosition);
                Toast.makeText(this, item + " is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mFooterAdapter) {
                Toast.makeText(this, "Footer is long clicked.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}
