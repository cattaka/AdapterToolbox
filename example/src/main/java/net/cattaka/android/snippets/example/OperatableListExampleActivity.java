package net.cattaka.android.snippets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.SimpleStringAdapter;
import net.cattaka.android.snippets.example.adapter.factory.MyInfoViewHolderFactory;
import net.cattaka.android.snippets.example.data.MyInfo;
import net.cattaka.android.snippets.example.data.OrdinalLabel;
import net.cattaka.android.snippets.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cattaka on 16/05/15.
 */
public class OperatableListExampleActivity extends AppCompatActivity {
    ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder>() {
        @Override
        public void onCheckedChanged(RecyclerView recyclerView, ScrambleAdapter adapter, RecyclerView.ViewHolder viewHolder, CompoundButton buttonView, boolean isChecked) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                item.setChecked(isChecked);
            }
        }

        @Override
        public void onProgressChanged(RecyclerView recyclerView, ScrambleAdapter adapter, RecyclerView.ViewHolder viewHolder, SeekBar seekBar, int progress, boolean fromUser) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                item.setIntValue(progress);
            }
        }

        @Override
        public void onItemSelected(RecyclerView recyclerView, ScrambleAdapter adapter, RecyclerView.ViewHolder viewHolder, AdapterView<?> parent, View view, int position, long id) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                item.setOrdinalLabel((OrdinalLabel) parent.getItemAtPosition(position));
            }
        }
    };

    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SimpleStringAdapter mSimpleStringAdapter;
    ScrambleAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mMergeRecyclerAdapter = new MergeRecyclerAdapter(this);
        {
            mSimpleStringAdapter = new SimpleStringAdapter(this, Collections.singletonList("This is header"));
            mMergeRecyclerAdapter.addAdapter(mSimpleStringAdapter);
        }
        {
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                items.add(new MyInfo());
            }
            mItemAdapter = new ScrambleAdapter(this, items, mListenerRelay, new MyInfoViewHolderFactory());
            mMergeRecyclerAdapter.addAdapter(mItemAdapter);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mMergeRecyclerAdapter);
    }

}
