package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.adapter.SingleViewAdapter;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
import net.cattaka.android.snippets.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class RecyclerViewHeaderExampleActivity extends AppCompatActivity {

    ListenerRelay<CustomRecyclerAdapter<RecyclerView.ViewHolder, Object>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<CustomRecyclerAdapter<RecyclerView.ViewHolder, Object>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(RecyclerView recyclerView, CustomRecyclerAdapter<RecyclerView.ViewHolder, Object> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mHeaderAdapter) {
                    Snackbar.make(view, "Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mItemsAdapter) {
                    String item = mItemsAdapter.getItemAt(la.mLocalPosition);
                    Snackbar.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    Snackbar.make(view, "Footer is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(RecyclerView recyclerView, CustomRecyclerAdapter<RecyclerView.ViewHolder, Object> adapter, RecyclerView.ViewHolder viewHolder, View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mHeaderAdapter) {
                    Snackbar.make(view, "Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mItemsAdapter) {
                    String item = mItemsAdapter.getItemAt(la.mLocalPosition);
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
                mHeaderAdapter.setListenerRelay(mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mHeaderAdapter);
            }
            {   // create items adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    items.add("item " + i);
                }

                mItemsAdapter = new SimpleStringAdapter(this, items);
                mItemsAdapter.setListenerRelay(mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mItemsAdapter);
            }
            {   // create footer adapter
                mFooterAdapter = new SingleViewAdapter(this, R.layout.view_footer);
                mFooterAdapter.setListenerRelay(mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mFooterAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }
}
