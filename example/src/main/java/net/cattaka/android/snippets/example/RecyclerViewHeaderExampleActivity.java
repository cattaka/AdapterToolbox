package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.adapter.SingleViewAdapter;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
import net.cattaka.android.snippets.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class RecyclerViewHeaderExampleActivity extends AppCompatActivity implements
        CustomRecyclerAdapter.OnItemClickListener<RecyclerView.ViewHolder, Object>,
        CustomRecyclerAdapter.OnItemLongClickListener<RecyclerView.ViewHolder, Object> {
    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter mHeaderAdapter;
    SimpleStringAdapter mItemsAdapter;
    SingleViewAdapter mFooterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_header_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create header adapter
                mHeaderAdapter = new SingleViewAdapter(this, R.layout.view_header);
                mHeaderAdapter.setOnItemClickListener(this);
                mHeaderAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mHeaderAdapter);
            }
            {   // create items adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    items.add("item " + i);
                }

                mItemsAdapter = new SimpleStringAdapter(this, items);
                mItemsAdapter.setOnItemClickListener(this);
                mItemsAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mItemsAdapter);
            }
            {   // create footer adapter
                mFooterAdapter = new SingleViewAdapter(this, R.layout.view_footer);
                mFooterAdapter.setOnItemClickListener(this);
                mFooterAdapter.setOnItemLongClickListener(this);
                mMergeRecyclerAdapter.addAdapter(mFooterAdapter);
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
            if (la.mAdapter == mHeaderAdapter) {
                Toast.makeText(this, "Header is clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mItemsAdapter) {
                String item = mItemsAdapter.getItemAt(la.mLocalPosition);
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
            if (la.mAdapter == mHeaderAdapter) {
                Toast.makeText(this, "Header is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mItemsAdapter) {
                String item = mItemsAdapter.getItemAt(la.mLocalPosition);
                Toast.makeText(this, item + " is long clicked.", Toast.LENGTH_SHORT).show();
            } else if (la.mAdapter == mFooterAdapter) {
                Toast.makeText(this, "Footer is long clicked.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}
