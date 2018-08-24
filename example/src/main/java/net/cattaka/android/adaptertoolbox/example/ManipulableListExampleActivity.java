package net.cattaka.android.adaptertoolbox.example;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.SimpleStringAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.MyInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.TextInfoViewHolderFactory;
import net.cattaka.android.adaptertoolbox.example.data.MyInfo;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.data.TextInfo;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cattaka on 16/05/15.
 */
public class ManipulableListExampleActivity extends AppCompatActivity {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onCheckedChanged(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull CompoundButton buttonView, boolean isChecked) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof MyInfoViewHolderFactory.ViewHolder) {
                    MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                    item.setChecked(isChecked);
                }
            }
        }

        @Override
        public void onProgressChanged(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull SeekBar seekBar, int progress, boolean fromUser) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof MyInfoViewHolderFactory.ViewHolder) {
                    MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                    item.setIntValue(progress);
                }
            }
        }

        @Override
        public void onItemSelected(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof MyInfoViewHolderFactory.ViewHolder) {
                    MyInfo item = (MyInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                    item.setOrdinalLabel((OrdinalLabel) parent.getItemAtPosition(position));
                }
            }
        }

        @Override
        public void afterTextChanged(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull TextView v, @NonNull Editable s) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof TextInfoViewHolderFactory.ViewHolder) {
                    TextInfo item = (TextInfo) mItemAdapter.getItemAt(la.mLocalPosition);
                    item.setText(String.valueOf(s));
                }
            }
        }

        @Override
        public boolean onEditorAction(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull TextView v, int actionId, KeyEvent event) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof TextInfoViewHolderFactory.ViewHolder) {
                    mSnackbarLogic.make(v, "Action button pressed : " + v.getText(), Snackbar.LENGTH_SHORT).show();
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            MergeRecyclerAdapter.LocalAdapter la = mMergeRecyclerAdapter.getAdapterOffsetForItem(viewHolder.getAdapterPosition());
            if (la.mAdapter == mItemAdapter) {
                if (viewHolder instanceof TextInfoViewHolderFactory.ViewHolder) {
                    TextInfoViewHolderFactory.ViewHolder vh = (TextInfoViewHolderFactory.ViewHolder) viewHolder;
                    mSnackbarLogic.make(view, "Action button pressed : " + vh.editText.getText(), Snackbar.LENGTH_SHORT).show();
                }
            }
        }
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
                switch (i % 2) {
                    case 0:
                        items.add(new MyInfo());
                        break;
                    case 1:
                        items.add(new TextInfo());
                        break;
                }
            }
            mItemAdapter = new ScrambleAdapter<>(this, items, mListenerRelay,
                    new MyInfoViewHolderFactory(),
                    new TextInfoViewHolderFactory()
            );
            mMergeRecyclerAdapter.addAdapter(mItemAdapter);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mMergeRecyclerAdapter);
    }
}
