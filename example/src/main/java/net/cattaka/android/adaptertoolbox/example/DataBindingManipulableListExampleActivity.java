package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.DataBindingMyInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.ObservableMyInfo;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cattaka on 16/05/15.
 */
public class DataBindingManipulableListExampleActivity extends AppCompatActivity {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        // no-implementations
    };

    SnackbarLogic mSnackbarLogic = new SnackbarLogic();

    RecyclerView mRecyclerView;
    MergeRecyclerAdapter<RecyclerView.Adapter> mMergeRecyclerAdapter;
    SimpleStringAdapter mSimpleStringAdapter;
    ScrambleAdapter<Object> mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulable_list_example);

        // find views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mMergeRecyclerAdapter = new MergeRecyclerAdapter<>(this);
        {
            mSimpleStringAdapter = new SimpleStringAdapter(this, Collections.singletonList("This is header"), null);
            mMergeRecyclerAdapter.addAdapter(mSimpleStringAdapter);
        }
        {
            List<Object> items = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                items.add(new ObservableMyInfo());
            }
            mItemAdapter = new ScrambleAdapter<>(this, items, mListenerRelay,
                    new DataBindingMyInfoViewHolderFactory()
            );
            mMergeRecyclerAdapter.addAdapter(mItemAdapter);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mMergeRecyclerAdapter);
    }
}
