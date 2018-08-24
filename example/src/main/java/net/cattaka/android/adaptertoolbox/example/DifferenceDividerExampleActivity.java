package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.SingleViewAdapter2;
import net.cattaka.android.adaptertoolbox.decoration.VerticalListDividerItemDecoration;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleNumberAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleNumberViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.SimpleStringViewHolderFactory;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/12/11.
 */

public class DifferenceDividerExampleActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SingleViewAdapter2 mStringsHeaderAdapter;
    SimpleStringAdapter mStringsAdapter;
    SingleViewAdapter2 mNumbersHeaderAdapter;
    SimpleNumberAdapter mNumbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference_divider);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        {   // prepare adapters
            mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
            {   // create strings header adapter
                mStringsHeaderAdapter = new SingleViewAdapter2(this, R.layout.view_header_string);
                mMergeRecyclerAdapter.addAdapter(mStringsHeaderAdapter);
            }
            {   // create strings adapter
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add("item " + i);
                }

                mStringsAdapter = new SimpleStringAdapter(this, items, null);
                mMergeRecyclerAdapter.addAdapter(mStringsAdapter);
            }
            {   // create numbers header adapter
                mNumbersHeaderAdapter = new SingleViewAdapter2(this, R.layout.view_header_number);
                mMergeRecyclerAdapter.addAdapter(mNumbersHeaderAdapter);
            }
            {   // create numbers adapter
                List<Number> items = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    items.add(i);
                }
                mNumbersAdapter = new SimpleNumberAdapter(this, items, null);
                mMergeRecyclerAdapter.addAdapter(mNumbersAdapter);
            }
            {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(mMergeRecyclerAdapter);
            }
        }
        {   // Set divider
            mRecyclerView.addItemDecoration(new VerticalListDividerItemDecoration(this, false, R.drawable.vertical_list_divider_rgb) {
                @Override
                public boolean isAssignable(@NonNull RecyclerView parent, RecyclerView.ViewHolder nextViewHolder, int prevViewType) {
                    return nextViewHolder instanceof SimpleStringViewHolderFactory.ViewHolder
                            && nextViewHolder.getItemViewType() == prevViewType;
                }
            });
            mRecyclerView.addItemDecoration(new VerticalListDividerItemDecoration(this, false, R.drawable.vertical_list_divider_gbr) {
                @Override
                public boolean isAssignable(@NonNull RecyclerView parent, RecyclerView.ViewHolder nextViewHolder, int prevViewType) {
                    return nextViewHolder instanceof SimpleNumberViewHolderFactory.ViewHolder
                            && nextViewHolder.getItemViewType() == prevViewType;
                }
            });
        }
    }
}
