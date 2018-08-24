package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.SingleViewAdapter2;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class RecyclerViewHeaderExampleActivity extends AppCompatActivity {

    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mItemsAdapter) {
                    String item = mItemsAdapter.getItemAt(la.mLocalPosition);
                    mSnackbarLogic.make(view, item + " is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mItemsAdapter) {
                    String item = mItemsAdapter.getItemAt(la.mLocalPosition);
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
                if (la.mAdapter == mHeaderAdapter) {
                    mSnackbarLogic.make(view, "Header is clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    mSnackbarLogic.make(view, "Footer is clicked.", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public boolean onLongClick(@NonNull RecyclerView recyclerView, @NonNull SingleViewAdapter2 adapter, @NonNull SingleViewAdapter2.ViewHolder viewHolder, @NonNull View view) {
            if (recyclerView.getId() == R.id.recycler) {
                MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
                if (la.mAdapter == mHeaderAdapter) {
                    mSnackbarLogic.make(view, "Header is long clicked.", Snackbar.LENGTH_SHORT).show();
                } else if (la.mAdapter == mFooterAdapter) {
                    mSnackbarLogic.make(view, "Footer is long clicked.", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter2 mHeaderAdapter;
    SimpleStringAdapter mItemsAdapter;
    SingleViewAdapter2 mFooterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_header_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create header adapter
                mHeaderAdapter = new SingleViewAdapter2(this, R.layout.view_header);
                mHeaderAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mHeaderAdapter);
            }
            {   // create items adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    items.add("item " + i);
                }

                mItemsAdapter = new SimpleStringAdapter(this, items, mListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mItemsAdapter);
            }
            {   // create footer adapter
                mFooterAdapter = new SingleViewAdapter2(this, R.layout.view_footer);
                mFooterAdapter.setListenerRelay(mHeaderListenerRelay);
                mMergeRecyclerAdapter.addAdapter(mFooterAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
    }
}
